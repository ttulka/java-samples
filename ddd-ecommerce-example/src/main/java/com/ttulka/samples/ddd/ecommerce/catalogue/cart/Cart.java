package com.ttulka.samples.ddd.ecommerce.catalogue.cart;

import java.util.List;

public interface Cart {

    List<CartItem> items();

    void add(CartItem toAdd);

    void remove(String productCode);

    void empty();
}
