package com.ttulka.ecommerce.catalogue;

import java.util.List;

import com.ttulka.ecommerce.catalogue.cart.Cart;
import com.ttulka.ecommerce.catalogue.cart.CartItem;

/**
 * List Cart Items use-case.
 */
public interface ListCartItems {

    /**
     * Lists items of the cart.
     *
     * @param cart the cart
     * @return items of the cart
     */
    List<CartItem> ofCart(Cart cart);
}
