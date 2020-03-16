package com.ttulka.samples.ddd.ecommerce.shipping.listeners;

import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.sales.OrderPlaced;
import com.ttulka.samples.ddd.ecommerce.shipping.PrepareDelivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Address;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryItem;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Person;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Place;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.ProductCode;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Quantity;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class OrderPlacedListener {

    private final @NonNull PrepareDelivery prepareDelivery;

    @TransactionalEventListener
    @Async
    public void on(OrderPlaced event) {
        prepareDelivery.prepare(
                new OrderId(event.orderId),
                event.orderItems.stream()
                        .map(item -> new DeliveryItem(
                                new ProductCode(item.code),
                                new Quantity(item.quantity)))
                        .collect(Collectors.toList()),
                new Address(
                        new Person(event.customer.name),
                        new Place(event.customer.address)));
    }
}
