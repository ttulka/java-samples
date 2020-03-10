package com.ttulka.samples.ddd.ecommerce.shipping;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ShipDelivery {

    private final @NonNull Deliveries deliveries;

    public void byOrderId(OrderId orderId) {
        deliveries.byOrderId(orderId).ship();
    }
}
