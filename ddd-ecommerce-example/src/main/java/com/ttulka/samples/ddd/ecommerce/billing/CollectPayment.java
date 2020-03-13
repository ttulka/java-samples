package com.ttulka.samples.ddd.ecommerce.billing;

import java.time.Instant;

import com.ttulka.samples.ddd.ecommerce.billing.payment.Payment;
import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CollectPayment {

    private final @NonNull EventPublisher eventPublisher;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void collect(Payment payment) {
        payment.collect();
        payment.confirm();

        eventPublisher.raise(new PaymentReceived(Instant.now(), payment.referenceId().value()));
    }
}
