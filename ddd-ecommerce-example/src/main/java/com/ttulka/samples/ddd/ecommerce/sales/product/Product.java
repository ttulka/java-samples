package com.ttulka.samples.ddd.ecommerce.sales.product;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class Product {

    private final ProductId id;
    private final Title title;
    private final Description description;
    private final Price price;

    public ProductId id() {
        return id;
    }

    public Title title() {
        return title;
    }

    public Description description() {
        return description;
    }

    public Price price() {
        return price;
    }
}
