package com.ttulka.samples.ddd.ecommerce.shipping.listeners;

import com.ttulka.samples.ddd.ecommerce.billing.PaymentReceived;
import com.ttulka.samples.ddd.ecommerce.shipping.UpdateDelivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;
import com.ttulka.samples.ddd.ecommerce.warehouse.GoodsFetched;

import org.springframework.transaction.event.TransactionalEventListener;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DispatchingListener {

    private final @NonNull UpdateDelivery updateDelivery;

    @TransactionalEventListener
    public void on(GoodsFetched event) {
        updateDelivery.asFetched(new OrderId(event.orderId));
    }

    @TransactionalEventListener
    public void on(PaymentReceived event) {
        updateDelivery.asPaid(new OrderId(event.referenceId));
    }
}
