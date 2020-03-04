package com.ttulka.samples.ddd.ecommerce.warehouse;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class InStock {

    private final int amount;

    public InStock(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount in stock cannot be less than zero!");
        }
        this.amount = amount;
    }

    public int amount() {
        return amount;
    }

    public InStock add(InStock addend) {
        return new InStock(amount + addend.amount());
    }
}
