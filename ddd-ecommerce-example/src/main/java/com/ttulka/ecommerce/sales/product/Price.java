package com.ttulka.ecommerce.sales.product;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class Price {

    private final float price;

    public Price(float price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be less than zero.");
        }
        this.price = price;
    }

    public float value() {
        return price;
    }
}
