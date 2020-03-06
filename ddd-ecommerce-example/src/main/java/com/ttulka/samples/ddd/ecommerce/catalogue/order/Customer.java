package com.ttulka.samples.ddd.ecommerce.catalogue.order;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public final class Customer {

    private final @NonNull Name name;
    private final @NonNull Address address;
}
