package com.ttulka.samples.ddd.ecommerce.catalogue.cart;

import com.ttulka.samples.ddd.ecommerce.sales.product.Code;
import com.ttulka.samples.ddd.ecommerce.sales.product.FindProducts;
import com.ttulka.samples.ddd.ecommerce.sales.product.Product;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class AddIntoCart {

    private final Cart cart;
    private final FindProducts findProducts;

    public void item(String productCode, int amount) {
        Product product = findProducts.byCode(new Code(productCode));
        cart.add(new Item(product.code().value(), product.title().value(), new Amount(amount)));
    }
}
