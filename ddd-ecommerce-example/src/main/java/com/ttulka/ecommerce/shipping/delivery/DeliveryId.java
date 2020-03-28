package com.ttulka.ecommerce.shipping.delivery;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Delivery ID domain primitive.
 */
@EqualsAndHashCode
@ToString
public final class DeliveryId {

    private final @NonNull String id;

    public DeliveryId(@NonNull Object id) {
        String idString = id.toString().trim();
        if (idString.isBlank()) {
            throw new IllegalArgumentException("ID cannot be empty!");
        }
        if (idString.length() > 64) {
            throw new IllegalArgumentException("ID cannot be longer than 64 characters!");
        }
        this.id = idString;
    }

    public String value() {
        return id;
    }
}