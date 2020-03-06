package com.ttulka.samples.ddd.ecommerce.catalogue.cart;

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

    public Amount add(Amount addend) {
        return new Amount(amount + addend.value());
    }
}
