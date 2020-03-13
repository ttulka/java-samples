package com.ttulka.samples.ddd.ecommerce.sales.order.jdbc;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.sales.PlaceOrder;
import com.ttulka.samples.ddd.ecommerce.sales.order.OrderItem;
import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Customer;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
final class PlaceOrderJdbc implements PlaceOrder {

    private final @NonNull JdbcTemplate jdbcTemplate;
    private final @NonNull EventPublisher eventPublisher;

    @Override
    public void place(@NonNull List<OrderItem> items, @NonNull Customer customer) {
        new OrderJdbc(items, customer, jdbcTemplate, eventPublisher)
                .place();
    }
}