package com.ttulka.samples.ddd.ecommerce.billing.payment;

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

    public ReferenceId referenceId() {
        return referenceId;
    }

    public void collect() {
        log.info("Payment collected... {}", this);
    }

    public void confirm() {
        log.info("Payment confirmed... {}", this);
    }
}
