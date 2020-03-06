package com.ttulka.samples.ddd.ecommerce.catalogue.cart;

import java.util.List;

public interface Cart {

    List<Item> items();

    void add(Item toAdd);

    void remove(String productCode);

    void empty();
}
