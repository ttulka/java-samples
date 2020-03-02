package com.ttulka.samples.ddd.ecommerce.catalogue.cart;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class Amount {

    private final int amount;

    public Amount(int amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("Amount must be at least one!");
        }
        this.amount = amount;
    }

    public int value() {
        return amount;
    }
}
