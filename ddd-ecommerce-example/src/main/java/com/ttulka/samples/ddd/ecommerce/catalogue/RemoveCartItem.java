package com.ttulka.samples.ddd.ecommerce.catalogue;

import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;

public interface RemoveCartItem {

    /**
     * Removes the item from the cart.
     *
     * @param cart        the cart
     * @param productCode the product code of the item
     */
    void fromCart(Cart cart, String productCode);
}
