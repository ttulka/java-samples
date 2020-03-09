package com.ttulka.samples.ddd.ecommerce.billing;

import java.time.Instant;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class OrderPaid {

    public final @NonNull Instant when;
    public final @NonNull String orderId;
}
