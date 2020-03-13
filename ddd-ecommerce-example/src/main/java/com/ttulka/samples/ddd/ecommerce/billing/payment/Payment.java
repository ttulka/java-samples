package com.ttulka.samples.ddd.ecommerce.billing.payment;

import java.time.Instant;

import com.ttulka.samples.ddd.ecommerce.billing.PaymentReceived;
import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@ToString(of = {"referenceId", "total"})
@Slf4j
public final class Payment {

    private final @NonNull ReferenceId referenceId;
    private final @NonNull Money total;
    private final @NonNull EventPublisher eventPublisher;

    public void confirm() {
        log.info("Payment received... {}", this);

        eventPublisher.raise(new PaymentReceived(Instant.now(), referenceId.value()));
    }
}
