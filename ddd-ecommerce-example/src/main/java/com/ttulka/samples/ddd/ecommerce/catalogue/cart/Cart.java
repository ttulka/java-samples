package com.ttulka.samples.ddd.ecommerce.catalogue.cart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "items")
public final class Cart {

    private List<Item> items = new ArrayList<>();

    public List<Item> items() {
        return Collections.unmodifiableList(items);
    }

    public void add(@NonNull Item item) {
        Optional<Item> alreadyInCart = items.stream()
                .filter(item::equals)
                .findAny();

        alreadyInCart.ifPresent(i -> i.add(item.amount()));

        if (!alreadyInCart.isPresent()) {
            items.add(item);
        }
    }

    public void empty() {
        items.clear();
    }
}
