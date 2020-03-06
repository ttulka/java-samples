package com.ttulka.samples.ddd.ecommerce.catalogue.order;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public final class Order {

    private final @NonNull List<OrderItem> items;
    private final @NonNull Customer customer;

    private final EventPublisher eventPublisher;

    public void submit() {
        // TODO
        eventPublisher.raise(new NewOrderCreated());
    }
}
