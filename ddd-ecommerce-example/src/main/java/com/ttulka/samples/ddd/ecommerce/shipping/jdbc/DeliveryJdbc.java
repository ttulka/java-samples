package com.ttulka.samples.ddd.ecommerce.shipping.jdbc;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.shipping.Address;
import com.ttulka.samples.ddd.ecommerce.shipping.Delivery;
import com.ttulka.samples.ddd.ecommerce.shipping.DeliveryId;
import com.ttulka.samples.ddd.ecommerce.shipping.DeliveryItem;
import com.ttulka.samples.ddd.ecommerce.shipping.OrderId;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "orderId", "items", "address"})
@Slf4j
public final class DeliveryJdbc implements Delivery {

    private final @NonNull DeliveryId id;
    private final @NonNull OrderId orderId;
    private final @NonNull List<DeliveryItem> items;
    private final @NonNull Address address;

    private final @NonNull JdbcTemplate jdbcTemplate;

    private boolean prepared = false;
    private boolean shipped = false;

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
        if (prepared) {
            throw new DeliveryAlreadyPreparedException();
        }
        jdbcTemplate.update("INSERT INTO deliveries VALUES (?, ?, ?, ?, FALSE)",
                            id.value(), orderId.value(), address.person().value(), address.place().value());

        items.forEach(item -> jdbcTemplate.update(
                "INSERT INTO delivery_items VALUES (?, ?, ?)",
                item.productCode().value(), item.amount().value(), id.value())
        );

        prepared = true;
    }

    @Override
    public void ship() {
        if (shipped) {
            throw new DeliveryAlreadyShippedException();
        }
        jdbcTemplate.update("UPDATE deliveries SET shipped = TRUE WHERE id = ?", id.value());

        // Some other delivery stuff
        log.info("Shipping...");
        log.info("Items: {}", items);
        log.info("To address: {}", address);

        shipped = true;
    }

    @Override
    public boolean isShipped() {
        return shipped;
    }
}
