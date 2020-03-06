package com.ttulka.samples.ddd.ecommerce.catalogue.order;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class Name {

    private final String name;

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
