package com.ttulka.ecommerce.shipping.delivery;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class Person {

    private final @NonNull String name;

    public Person(@NonNull String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Person cannot be empty!");
        }
        if (name.trim().length() > 50) {
            throw new IllegalArgumentException("Person cannot be longer than 50 characters!");
        }
        this.name = name.trim();
    }

    public String value() {
        return name;
    }
}
