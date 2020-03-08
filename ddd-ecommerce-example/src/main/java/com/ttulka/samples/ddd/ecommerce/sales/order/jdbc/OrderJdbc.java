package com.ttulka.samples.ddd.ecommerce.sales.order.jdbc;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.sales.order.Order;
import com.ttulka.samples.ddd.ecommerce.sales.order.OrderItem;
import com.ttulka.samples.ddd.ecommerce.sales.order.OrderPlaced;
import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Customer;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString(exclude = "eventPublisher")
final class OrderJdbc implements Order {

    private final @NonNull List<OrderItem> items;
    private final @NonNull Customer customer;

    private final @NonNull EventPublisher eventPublisher;

    // TODO do in a transaction
    @Override
    public void place() {
        // TODO save the order into the database
        // ...
        eventPublisher.raise(new OrderPlaced(toString()));
    }
}
