package com.ttulka.samples.ddd.ecommerce.sales.category;

import lombok.NonNull;

public final class Title {

    private final String title;

    public Title(@NonNull String title) {
        if (title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty!");
        }
        if (title.trim().length() > 20) {
            throw new IllegalArgumentException("Title cannot be longer than 20 characters!");
        }
        this.title = title.trim();
    }

    public String value() {
        return title;
    }
}
