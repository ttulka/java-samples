package com.ttulka.ecommerce.shipping.delivery.jdbc;

import java.util.List;

import com.ttulka.ecommerce.common.events.EventPublisher;
import com.ttulka.ecommerce.shipping.PrepareDelivery;
import com.ttulka.ecommerce.shipping.delivery.Address;
import com.ttulka.ecommerce.shipping.delivery.Delivery;
import com.ttulka.ecommerce.shipping.delivery.DeliveryItem;
import com.ttulka.ecommerce.shipping.delivery.OrderId;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for Prepare Delivery use-cases.
 */
@RequiredArgsConstructor
@Slf4j
class PrepareDeliveryJdbc implements PrepareDelivery {

    private final @NonNull JdbcTemplate jdbcTemplate;
    private final @NonNull EventPublisher eventPublisher;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void prepare(@NonNull OrderId orderId, @NonNull List<DeliveryItem> items, @NonNull Address address) {
        Delivery delivery = new DeliveryJdbc(orderId, items, address, jdbcTemplate, eventPublisher);
        delivery.prepare();

        if (isFetched(orderId)) {
            delivery.markAsFetched();
        }
        if (isPaid(orderId)) {
            delivery.markAsPaid();
        }
    }

    private boolean isFetched(OrderId orderId) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(order_id) FROM delivery_fetched " +
                "WHERE order_id = ?", Integer.class, orderId.value()) > 0;
    }

    private boolean isPaid(OrderId orderId) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(order_id) FROM delivery_paid " +
                "WHERE order_id = ?", Integer.class, orderId.value()) > 0;
    }
}
