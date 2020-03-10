package com.ttulka.samples.ddd.ecommerce.shipping;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public final class Address {

    private final @NonNull Person person;
    private final @NonNull Place place;

    public Person person() {
        return person;
    }

    public Place place() {
        return place;
    }
}
