package com.ttulka.samples.ddd.ecommerce.sales.product;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
public final class ProductId {

    private final Long id;

    public String value() {
        return id.toString();
    }
}