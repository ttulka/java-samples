package com.ttulka.samples.ddd.ecommerce.sales.product;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class Description {

    private final String description;

    public String value() {
        return description;
    }
}
