package com.ttulka.samples.ddd.ecommerce.sales;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.sales.order.Order;
import com.ttulka.samples.ddd.ecommerce.sales.order.OrderItem;
import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Customer;

import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

@ToString
public final class OrderPlaced {

    public final @NonNull Instant when;
    public final @NonNull Object orderId;
    public final @NonNull List<OrderItemData> orderItems;
    public final @NonNull CustomerData customer;

    public OrderPlaced(@NonNull Instant when, @NonNull Order order) {
        this.when = when;
        this.orderId = order.id().value();
        this.orderItems = order.items().stream()
                .map(this::toData)
                .collect(Collectors.toList());
        this.customer = toData(order.customer());
    }

    private OrderItemData toData(OrderItem item) {
        return new OrderItemData(item.code(), item.title(), item.price(), item.quantity());
    }

    private CustomerData toData(Customer customer) {
        return new CustomerData(customer.name().value(), customer.address().value());
    }

    @Value
    public static final class OrderItemData {

        public final String code;
        public final String title;
        public final Float price;
        public final Integer quantity;
    }

    @Value
    public static final class CustomerData {

        public final String name;
        public final String address;
    }
}
