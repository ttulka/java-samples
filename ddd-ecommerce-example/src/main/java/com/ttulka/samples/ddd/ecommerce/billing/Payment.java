package com.ttulka.samples.ddd.ecommerce.billing;

import java.time.Instant;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class Payment {

    private final @NonNull String orderId;
    private final @NonNull EventPublisher eventPublisher;

    public void confirm() {
        // TODO
        eventPublisher.raise(new OrderPaid(Instant.now(), orderId));
    }
}
