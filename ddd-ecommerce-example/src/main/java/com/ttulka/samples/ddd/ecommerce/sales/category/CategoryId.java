package com.ttulka.samples.ddd.ecommerce.sales.category;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class CategoryId {

    private final @NonNull String id;

    public CategoryId(@NonNull Object id) {
        String idString = id.toString().trim();
        if (idString.isBlank()) {
            throw new IllegalArgumentException("ID cannot be empty!");
        }
        if (idString.length() > 64) {
            throw new IllegalArgumentException("ID cannot be longer than 64 characters!");
        }
        this.id = idString;
    }

    public String value() {
        return id;
    }
}
