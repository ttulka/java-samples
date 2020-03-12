package com.ttulka.samples.ddd.ecommerce.shipping.delivery;

import java.util.Collections;
import java.util.List;

public final class UnknownDelivery implements Delivery {

    @Override
    public DeliveryId id() {
        return new DeliveryId(0);
    }

    @Override
    public OrderId orderId() {
        return new OrderId(0);
    }

    @Override
    public List<DeliveryItem> items() {
        return Collections.emptyList();
    }

    @Override
    public Address address() {
        return new Address(
                new Person("unknown"),
                new Place("unknown"));
    }

    @Override
    public void prepare() {
        // do nothing
    }

    @Override
    public void dispatch() {
        // do nothing
    }

    @Override
    public boolean isDispatched() {
        return false;
    }
}
