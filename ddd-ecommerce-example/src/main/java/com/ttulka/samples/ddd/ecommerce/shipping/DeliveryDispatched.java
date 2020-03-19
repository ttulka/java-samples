package com.ttulka.samples.ddd.ecommerce.shipping;

import java.time.Instant;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "orderId")
@ToString
public final class DeliveryDispatched {

    public final @NonNull Instant when;
    public final @NonNull String orderId;
}
