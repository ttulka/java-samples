package com.ttulka.samples.ddd.ecommerce.catalogue;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.CartItem;

public interface ListCartItems {

    List<CartItem> ofCart(Cart cart);
}
