package com.ttulka.samples.ddd.ecommerce.sales.order;

import java.time.Instant;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

// TODO
@Getter
@RequiredArgsConstructor
public final class OrderPlaced {

    private final @NonNull Instant when;
    private final @NonNull Order order;
}
