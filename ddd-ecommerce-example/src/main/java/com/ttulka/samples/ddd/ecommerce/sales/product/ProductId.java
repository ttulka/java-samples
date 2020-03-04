package com.ttulka.samples.ddd.ecommerce.sales.product;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public final class ProductId {

    private final @NonNull Object id;

    public Object value() {
        return id;
    }
}