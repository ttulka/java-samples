package com.ttulka.ecommerce.shipping.delivery;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Delivery Place domain primitive.
 */
@EqualsAndHashCode
@ToString
public final class Place {

    private final @NonNull String place;

    public Place(@NonNull String place) {
        if (place.isBlank()) {
            throw new IllegalArgumentException("Place cannot be empty!");
        }
        if (place.trim().length() > 100) {
            throw new IllegalArgumentException("Place cannot be longer than 100 characters!");
        }
        this.place = place.trim();
    }

    public String value() {
        return place;
    }
}
