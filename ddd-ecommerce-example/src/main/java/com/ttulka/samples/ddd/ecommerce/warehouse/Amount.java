package com.ttulka.samples.ddd.ecommerce.warehouse;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class Amount {

    private final int amount;

    public Amount(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be less than zero!");
        }
        this.amount = amount;
    }

    public int value() {
        return amount;
    }
}
