package com.ttulka.samples.ddd.ecommerce.shipping;

import java.util.List;

public interface Delivery {

    DeliveryId id();

    OrderId orderId();

    List<DeliveryItem> items();

    Address address();

    void prepare();

    void ship();

    boolean isShipped();

    final class DeliveryAlreadyPreparedException extends IllegalStateException {
    }

    final class DeliveryAlreadyShippedException extends IllegalStateException {
    }
}
