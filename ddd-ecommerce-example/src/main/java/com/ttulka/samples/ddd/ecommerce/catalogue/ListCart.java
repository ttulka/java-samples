package com.ttulka.samples.ddd.ecommerce.catalogue;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.CartItem;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ListCart {

    private final Cart cart;

    public List<CartItem> items() {
        return cart.items();
    }
}
