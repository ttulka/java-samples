package com.ttulka.ecommerce.warehouse;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class ProductCode {

    private final @NonNull String code;

    public ProductCode(@NonNull String code) {
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
