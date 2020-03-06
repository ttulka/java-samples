package com.ttulka.samples.ddd.ecommerce.catalogue.cart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "items")
public final class CartInMemory implements Cart {

    private final List<Item> items = new ArrayList<>();

    @Override
    public List<Item> items() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public void add(@NonNull Item toAdd) {
        Amount alreadyInCart = new Amount(0);
        for (Iterator<Item> it = items.iterator(); it.hasNext(); ) {
            Item item = it.next();
            if (item.equals(toAdd)) {
                alreadyInCart = item.amount();
                it.remove();
            }
        }
        items.add(toAdd.add(alreadyInCart));
    }

    @Override
    public void empty() {
        items.clear();
    }
}
