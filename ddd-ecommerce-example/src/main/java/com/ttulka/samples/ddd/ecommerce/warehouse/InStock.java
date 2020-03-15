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

    public InStock add(Amount addend) {
        return new InStock(amount + addend.value());
    }

    public InStock remove(Amount addend) {
        return new InStock(Math.max(0, amount - addend.value()));
    }

    public boolean has(Amount amount) {
        return this.amount >= amount.value();
    }

    public Amount needsYet(Amount amount) {
        return new Amount(this.amount > amount.value() ? 0 : amount.value() - this.amount);
    }

    public boolean isSoldOut() {
        return amount == 0;
    }
}
