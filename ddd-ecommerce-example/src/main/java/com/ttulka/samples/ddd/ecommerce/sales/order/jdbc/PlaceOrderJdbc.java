package com.ttulka.samples.ddd.ecommerce.sales.order.jdbc;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.sales.order.OrderItem;
import com.ttulka.samples.ddd.ecommerce.sales.order.PlaceOrder;
import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Customer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
final class PlaceOrderJdbc implements PlaceOrder {

    private final @NonNull EventPublisher eventPublisher;

    @Override
    public void place(@NonNull List<OrderItem> items, @NonNull Customer customer) {
        new OrderJdbc(items, customer, eventPublisher)
                .place();
    }
}
