package com.ttulka.samples.ddd.ecommerce.billing;

import java.time.Instant;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public final class OrderPaid {

    public final @NonNull Instant when;
    public final @NonNull Object orderId;
}
