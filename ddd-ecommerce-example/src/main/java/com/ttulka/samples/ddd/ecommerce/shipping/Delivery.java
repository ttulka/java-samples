package com.ttulka.samples.ddd.ecommerce.shipping;

import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public final class Delivery {

    private final @NonNull List<Item> items;
    private final @NonNull Address address;

    public void ship() {
        log.info("Shipping...");
        log.info("Items: {}", items);
        log.info("To address: {}", address);

        // Some other delivery stuff
    }

    @Value
    public static final class Item {

        private final @NonNull String productCode;
        private final @NonNull int amount;
    }

    @Value
    public static final class Address {

        private final @NonNull String person;
        private final @NonNull String address;
    }
}
