package com.ttulka.samples.ddd.ecommerce.sales.category;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class Category {

    private final CategoryId id;
    private final Title title;

    public CategoryId id() {
        return id;
    }

    public Title title() {
        return title;
    }
}
