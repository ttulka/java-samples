package com.ttulka.samples.ddd.ecommerce.sales.order.customer;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class Address {

    private final String address;

    public Address(@NonNull String address) {
        if (address.isBlank()) {
            throw new IllegalArgumentException("Address cannot be empty!");
        }
        if (address.trim().length() > 100) {
            throw new IllegalArgumentException("Address cannot be longer than 100 characters!");
        }
        this.address = address.trim();
    }

    public String value() {
        return address;
    }
}
