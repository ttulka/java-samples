package com.ttulka.ecommerce.shipping.delivery.jdbc;

import java.util.Collections;
import java.util.List;

import com.ttulka.ecommerce.shipping.delivery.Address;
import com.ttulka.ecommerce.shipping.delivery.Delivery;
import com.ttulka.ecommerce.shipping.delivery.DeliveryId;
import com.ttulka.ecommerce.shipping.delivery.DeliveryItem;
import com.ttulka.ecommerce.shipping.delivery.OrderId;
import com.ttulka.ecommerce.shipping.delivery.Person;
import com.ttulka.ecommerce.shipping.delivery.Place;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
final class UnknownDeliveryJdbc implements Delivery {

    private final @NonNull OrderId orderId;

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public DeliveryId id() {
        return new DeliveryId(0);
    }

    @Override
    public OrderId orderId() {
        return orderId;
    }

    @Override
    public List<DeliveryItem> items() {
        return Collections.emptyList();
    }

    @Override
    public Address address() {
        return new Address(
                new Person("unknown"),
                new Place("unknown"));
    }

    @Override
    public void prepare() {
        // do nothing
    }

    @Override
    public void markAsFetched() {
        try {
            jdbcTemplate.update("INSERT INTO delivery_fetched VALUES (?)", orderId.value());
        } catch (Exception ignore) {
            log.warn("Cannot mark a delivery as fetched for the order " + orderId, ignore);
        }
    }

    @Override
    public void markAsPaid() {
        try {
            jdbcTemplate.update("INSERT INTO delivery_paid VALUES (?)", orderId.value());
        } catch (Exception ignore) {
            log.warn("Cannot mark a delivery as paid for the order " + orderId, ignore);
        }
    }

    @Override
    public void dispatch() {
        throw new DeliveryNotReadyToBeDispatchedException();
    }

    @Override
    public boolean isDispatched() {
        return false;
    }

    @Override
    public boolean isReadyToDispatch() {
        return false;
    }
}
