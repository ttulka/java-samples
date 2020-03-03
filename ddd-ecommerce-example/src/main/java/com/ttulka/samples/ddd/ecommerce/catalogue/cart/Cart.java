package com.ttulka.samples.ddd.ecommerce.catalogue.cart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ttulka.samples.ddd.ecommerce.sales.product.FindProducts;
import com.ttulka.samples.ddd.ecommerce.sales.product.Product;
import com.ttulka.samples.ddd.ecommerce.sales.product.ProductId;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "items")
public final class Cart {

    private final FindProducts findProducts;

    private final List<Item> items = new ArrayList<>();

    public List<Item> items() {
        return Collections.unmodifiableList(items);
    }

    public void add(Long productId, int amount) {
        Product product = findProducts.byId(new ProductId(productId));
        items.add(new Item(
                product.id().value(),
                product.title().value(),
                new Amount(amount))
        );
    }
}
