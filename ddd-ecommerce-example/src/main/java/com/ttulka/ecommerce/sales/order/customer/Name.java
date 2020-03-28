package com.ttulka.ecommerce.sales.order.customer;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Customer Name domain primitive.
 */
@EqualsAndHashCode
@ToString
public final class Name {

    private final @NonNull String name;

    public Name(@NonNull String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        if (name.trim().length() > 50) {
            throw new IllegalArgumentException("Name cannot be longer than 50 characters!");
        }
        this.name = name.trim();
    }

    public String value() {
        return name;
    }
}
