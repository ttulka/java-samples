package com.ttulka.samples.ddd.ecommerce.shipping;

import java.util.List;

public interface Delivery {

    DeliveryId id();

    OrderId orderId();

    List<DeliveryItem> items();

    Address address();

    void prepare();

    void dispatch();

    boolean isDispatched();

    final class DeliveryAlreadyPreparedException extends IllegalStateException {
    }

    final class DeliveryAlreadyDispatchedException extends IllegalStateException {
    }
}
