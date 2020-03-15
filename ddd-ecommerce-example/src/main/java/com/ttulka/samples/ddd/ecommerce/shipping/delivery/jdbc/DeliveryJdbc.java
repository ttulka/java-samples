package com.ttulka.samples.ddd.ecommerce.shipping.delivery.jdbc;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.shipping.DeliveryDispatched;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Address;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Delivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryItem;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "orderId", "status", "items", "address"})
@Slf4j
final class DeliveryJdbc implements Delivery {

    private static final AtomicLong idSequence = new AtomicLong(); // TODO

    private final @NonNull DeliveryId id;
    private final @NonNull OrderId orderId;
    private final @NonNull List<DeliveryItem> items;
    private final @NonNull Address address;

    private @NonNull Status status = Status.NEW;

    private final @NonNull JdbcTemplate jdbcTemplate;
    private final @NonNull EventPublisher eventPublisher;

    public DeliveryJdbc(@NonNull OrderId orderId, @NonNull List<DeliveryItem> items, @NonNull Address address,
                        @NonNull JdbcTemplate jdbcTemplate, @NonNull EventPublisher eventPublisher) {
        this.id = new DeliveryId(idSequence.incrementAndGet());
        this.orderId = orderId;
        this.items = items;
        this.address = address;
        this.jdbcTemplate = jdbcTemplate;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public DeliveryId id() {
        return id;
    }

    @Override
    public OrderId orderId() {
        return orderId;
    }

    @Override
    public List<DeliveryItem> items() {
        return items;
    }

    @Override
    public Address address() {
        return address;
    }

    @Override
    public void prepare() {
        if (status != Status.NEW) {
            throw new DeliveryAlreadyPreparedException();
        }
        status = Status.PREPARED;

        jdbcTemplate.update("INSERT INTO deliveries VALUES (?, ?, ?, ?, ?)",
                            id.value(), orderId.value(), address.person().value(), address.place().value(), status.name());

        items.forEach(item -> jdbcTemplate.update(
                "INSERT INTO delivery_items VALUES (?, ?, ?)",
                item.productCode().value(), item.quantity().value(), id.value())
        );

        log.info("Delivery prepared... {}", this);
    }

    @Override
    public void markAsFetched() {
        switch (status) {
            case NEW:
            case PREPARED:
                status = Status.FETCHED;
                break;
            case PAID:
                status = Status.READY;
                break;
        }
        updateStatus();

        log.debug("Delivery marked as fetched... {}", this);
    }

    @Override
    public void markAsPaid() {
        switch (status) {
            case NEW:
            case PREPARED:
                status = Status.PAID;
                break;
            case FETCHED:
                status = Status.READY;
                break;
        }
        updateStatus();

        log.debug("Delivery marked as paid... {}", this);
    }

    @Override
    public void dispatch() {
        if (Status.DISPATCHED == status) {
            throw new DeliveryAlreadyDispatchedException();
        }
        if (Status.READY != status) {
            throw new DeliveryNotReadyToBeDispatchedException();
        }
        status = Status.DISPATCHED;
        updateStatus();

        eventPublisher.raise(new DeliveryDispatched(Instant.now(), orderId.value()));

        log.info("Delivery dispatched... {}", this);
    }

    @Override
    public boolean isDispatched() {
        return Status.DISPATCHED == status;
    }

    @Override
    public boolean isReadyToDispatch() {
        return Status.READY == status;
    }

    private void updateStatus() {
        jdbcTemplate.update("UPDATE deliveries SET status = ? WHERE id = ?", status.name(), id.value());
    }
}
