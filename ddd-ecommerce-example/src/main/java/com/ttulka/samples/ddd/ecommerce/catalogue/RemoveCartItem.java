package com.ttulka.samples.ddd.ecommerce.catalogue;

import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;

public interface RemoveCartItem {

    void fromCart(Cart cart, String productCode);
}
