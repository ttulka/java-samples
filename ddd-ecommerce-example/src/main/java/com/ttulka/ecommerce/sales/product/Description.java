package com.ttulka.ecommerce.sales.product;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Product Description domain primitive.
 */
@EqualsAndHashCode
@ToString
public final class Description {

    private final @NonNull String description;

    public Description(@NonNull String description) {
        if (description != null && description.trim().length() > 50) {
            throw new IllegalArgumentException("Description cannot be longer than 50 characters!");
        }
        this.description = description != null ? description.trim() : "";
    }

    public String value() {
        return description;
    }
}
