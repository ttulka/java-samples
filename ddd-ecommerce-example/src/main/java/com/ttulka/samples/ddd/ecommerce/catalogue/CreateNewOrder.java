package com.ttulka.samples.ddd.ecommerce.catalogue;

import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreateNewOrder {

    private final Cart cart;

    public void forCustomer(String name, String address) {
        // TODO
    }
}
