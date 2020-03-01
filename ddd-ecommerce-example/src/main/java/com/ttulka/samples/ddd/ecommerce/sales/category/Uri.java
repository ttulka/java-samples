package com.ttulka.samples.ddd.ecommerce.sales.category;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode
public final class Uri {

    private final String uri;

    public Uri(@NonNull String uri) {
        if (uri.trim().isEmpty()) {
            throw new IllegalArgumentException("URI cannot be empty!");
        }
        if (uri.trim().length() > 20) {
            throw new IllegalArgumentException("URI cannot be longer than 20 characters!");
        }
        this.uri = uri.trim();
    }

    public String value() {
        return uri;
    }
}
