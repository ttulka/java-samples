package com.ttulka.samples.ddd.ecommerce.billing.payment;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class Money {

    private final double money;

    public Money(double money) {
        if (money < 0) {
            throw new IllegalArgumentException("Money cannot be less than zero.");
        }
        this.money = money;
    }

    public double value() {
        return money;
    }
}
