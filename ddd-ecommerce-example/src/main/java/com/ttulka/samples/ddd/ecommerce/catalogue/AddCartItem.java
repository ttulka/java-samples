package com.ttulka.samples.ddd.ecommerce.catalogue;

import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;

public interface AddCartItem {

    void intoCart(Cart cart, String productCode, int quantity);
}
