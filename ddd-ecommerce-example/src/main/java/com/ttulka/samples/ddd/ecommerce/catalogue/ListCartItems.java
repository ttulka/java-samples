package com.ttulka.samples.ddd.ecommerce.catalogue;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.CartItem;

public interface ListCartItems {

    /**
     * Lists items of the cart.
     *
     * @param cart the cart
     * @return items of the cart
     */
    List<CartItem> ofCart(Cart cart);
}
