package com.ttulka.samples.ddd.ecommerce.sales.product;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class Code {

    private final String code;

    public Code(@NonNull String code) {
        if (code.trim().isEmpty()) {
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
