package com.ttulka.samples.ddd.ecommerce.catalogue.cart;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public final class Item {

    private final String productId;
    private final String title;
    private final Amount amount;

    public String productId() {
        return productId;
    }

    public String title() {
        return title;
    }

    public int amount() {
        return amount.value();
    }
}
