package com.ttulka.samples.ddd.ecommerce.sales.order;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Customer;

import lombok.NonNull;
import lombok.Value;

public final class OrderPlaced {

    public final @NonNull Instant when;
    public final @NonNull String orderId;
    public final @NonNull List<OrderItemData> orderItems;
    public final @NonNull CustomerData customer;

    public OrderPlaced(@NonNull Instant when, @NonNull Order order) {
        this.when = when;
        this.orderId = UUID.randomUUID().toString(); // TODO
        this.orderItems = order.items().stream()
                .map(this::toData)
                .collect(Collectors.toList());
        this.customer = toData(order.customer());
    }

    private OrderItemData toData(OrderItem item) {
        return new OrderItemData(item.code(), item.title(), item.price(), item.amount());
    }

    private CustomerData toData(Customer customer) {
        return new CustomerData(customer.name().value(), customer.address().value());
    }

    @Value
    public static final class OrderItemData {

        public final String code;
        public final String title;
        public final Float price;
        public final Integer amount;
    }

    @Value
    public static final class CustomerData {

        public final String name;
        public final String address;
    }
}
