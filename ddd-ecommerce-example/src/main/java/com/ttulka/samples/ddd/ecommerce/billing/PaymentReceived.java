package com.ttulka.samples.ddd.ecommerce.billing;

import java.time.Instant;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public final class PaymentReceived {

    public final @NonNull Instant when;
    public final @NonNull Object referenceId;
}
