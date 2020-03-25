package com.ttulka.ecommerce.catalogue;

import com.ttulka.ecommerce.catalogue.cart.Cart;

public interface AddCartItem {

    /**
     * Adds an item into the cart.
     *
     * @param cart        the cart
     * @param productCode the product code of the item
     * @param quantity    the quantity
     */
    void intoCart(Cart cart, String productCode, int quantity);
}
