package com.ttulka.samples.ddd.ecommerce.shipping.delivery;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public final class DeliveryId {

    private final @NonNull Object id;

    public Object value() {
        return id;
    }
}