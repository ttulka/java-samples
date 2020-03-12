package com.ttulka.samples.ddd.ecommerce.shipping;

import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DispatchDelivery {

    private final @NonNull FindDeliveries findDeliveries;

    public void byOrderId(OrderId orderId) {
        findDeliveries.byOrderId(orderId).dispatch();
    }
}
