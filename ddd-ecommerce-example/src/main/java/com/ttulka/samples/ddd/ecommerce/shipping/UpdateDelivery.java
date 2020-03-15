package com.ttulka.samples.ddd.ecommerce.shipping;

import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Delivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateDelivery {

    private final @NonNull FindDeliveries findDeliveries;

    public void asFetched(OrderId orderId) {
        Delivery delivery = findDeliveries.byOrderId(orderId);
        delivery.markAsFetched();

        if (delivery.isReadyToDispatch()) {
            dispatch(delivery);
        }
    }

    public void asPaid(OrderId orderId) {
        Delivery delivery = findDeliveries.byOrderId(orderId);
        delivery.markAsPaid();

        if (delivery.isReadyToDispatch()) {
            dispatch(delivery);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void dispatch(Delivery delivery) {
        delivery.dispatch();
    }
}
