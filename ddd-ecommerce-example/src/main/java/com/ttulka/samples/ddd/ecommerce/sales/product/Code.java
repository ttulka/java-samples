package com.ttulka.samples.ddd.ecommerce.sales.product;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode
public final class Code {

    private final String code;

    public Code(@NonNull String code) {
        if (code.trim().length() != 36) {
            throw new IllegalArgumentException("Code must be exactly 36 characters long!");
        }
        this.code = code.trim();
    }

    public String value() {
        return code;
    }
}
