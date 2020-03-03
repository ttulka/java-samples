package com.ttulka.samples.ddd.ecommerce.sales.product;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
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
