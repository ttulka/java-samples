package com.ttulka.ecommerce.sales.product;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Product Code domain primitive.
 */
@EqualsAndHashCode
@ToString
public final class Code {

    private final @NonNull String code;

    public Code(@NonNull String code) {
        if (code.isBlank()) {
            throw new IllegalArgumentException("Code cannot be empty!");
        }
        if (code.trim().length() > 50) {
            throw new IllegalArgumentException("Code cannot be longer than 50 characters!");
        }
        this.code = code.trim();
    }

    public String value() {
        return code;
    }
}
