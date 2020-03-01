package com.ttulka.samples.ddd.ecommerce.sales.category;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
public final class CategoryId {

    private final Long id;

    public String value() {
        return id.toString();
    }
}
