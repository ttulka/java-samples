package com.ttulka.samples.ddd.ecommerce.sales.category;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class Uri {

    private final @NonNull String uri;

    public Uri(@NonNull String uri) {
        if (uri.isBlank()) {
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
