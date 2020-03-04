package com.ttulka.samples.ddd.ecommerce.catalogue;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Item;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ListCart {

    private final Cart cart;

    public List<Item> items() {
        return cart.items();
    }
}
