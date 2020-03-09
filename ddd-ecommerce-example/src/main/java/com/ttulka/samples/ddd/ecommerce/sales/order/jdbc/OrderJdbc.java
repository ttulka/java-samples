package com.ttulka.samples.ddd.ecommerce.sales.order.jdbc;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.sales.order.OrderItem;
import com.ttulka.samples.ddd.ecommerce.sales.order.OrderPlaced;
import com.ttulka.samples.ddd.ecommerce.sales.order.PlaceableOrder;
import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Customer;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@ToString(exclude = "eventPublisher")
final class OrderJdbc implements PlaceableOrder {

    private final @NonNull List<OrderItem> items;
    private final @NonNull Customer customer;

    private final @NonNull EventPublisher eventPublisher;

    private volatile boolean placed = false;

    public OrderJdbc(@NonNull List<OrderItem> items, @NonNull Customer customer, @NonNull EventPublisher eventPublisher) {
        if (items.isEmpty()) {
            throw new OrderHasNoItemsException();
        }
        this.items = items;
        this.customer = customer;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<OrderItem> items() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public Customer customer() {
        return customer;
    }

    // TODO do in a transaction
    @Override
    public void place() {
        if (placed) {
            throw new OrderAlreadyPlacedException();
        }
        // TODO save the order into the database
        // ...
        eventPublisher.raise(new OrderPlaced(Instant.now(), this));
        placed = true;
    }
}
