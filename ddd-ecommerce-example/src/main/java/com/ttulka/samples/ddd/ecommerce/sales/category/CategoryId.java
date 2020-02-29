package com.ttulka.samples.ddd.ecommerce.sales.category;

import lombok.NonNull;

public final class CategoryId {

    private final String id;

    public CategoryId(@NonNull String id) {
        if (id.trim().isEmpty()) {
            throw new IllegalArgumentException("Category ID cannot be empty!");
        }
        if (id.trim().length() > 20) {
            throw new IllegalArgumentException("Category ID cannot be longer than 20 characters!");
        }
        this.id = id;
    }

    public String value() {
        return id;
    }
}
