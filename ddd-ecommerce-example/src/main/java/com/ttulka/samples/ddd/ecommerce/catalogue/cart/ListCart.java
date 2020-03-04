package com.ttulka.samples.ddd.ecommerce.catalogue.cart;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ListCart {

    private final Cart cart;

    public List<Item> items() {
        return cart.items();
    }
}
