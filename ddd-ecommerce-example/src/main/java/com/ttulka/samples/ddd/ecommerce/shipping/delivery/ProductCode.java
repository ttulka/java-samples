package com.ttulka.samples.ddd.ecommerce.shipping.delivery;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class ProductCode {

    private final @NonNull String code;

    public ProductCode(@NonNull String code) {
        if (code.isBlank()) {
            throw new IllegalArgumentException("Product code cannot be empty!");
        }
        if (code.trim().length() > 50) {
            throw new IllegalArgumentException("Product code cannot be longer than 50 characters!");
        }
        this.code = code.trim();
    }

    public String value() {
        return code;
    }
}
