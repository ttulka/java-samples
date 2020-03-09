package com.ttulka.samples.ddd.ecommerce.shipping;

import java.util.List;

//TODO
public final class Deliveries {

    public Delivery byOrderId(String orderId) {
        return new Delivery(List.of(new Delivery.Item("fake", 1)), new Delivery.Address("fake", "fake"));
    }
}
