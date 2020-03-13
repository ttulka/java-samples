package com.ttulka.samples.ddd.ecommerce.shipping;

import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Delivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdateDelivery {

    private final @NonNull FindDeliveries findDeliveries;

    public void asFetched(OrderId orderId) {
        Delivery delivery = findDeliveries.byOrderId(orderId);
        delivery.markAsFetched();

        if (delivery.isReadyToDispatch()) {
            delivery.dispatch();
        }
    }

    public void asPaid(OrderId orderId) {
        Delivery delivery = findDeliveries.byOrderId(orderId);
        delivery.markAsPaid();

        if (delivery.isReadyToDispatch()) {
            delivery.dispatch();
        }
    }
}
