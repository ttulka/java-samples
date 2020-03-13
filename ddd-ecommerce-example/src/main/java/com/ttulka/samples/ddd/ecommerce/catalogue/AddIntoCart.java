package com.ttulka.samples.ddd.ecommerce.catalogue;

import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.CartItem;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Quantity;
import com.ttulka.samples.ddd.ecommerce.sales.FindProducts;
import com.ttulka.samples.ddd.ecommerce.sales.product.Code;
import com.ttulka.samples.ddd.ecommerce.sales.product.Product;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class AddIntoCart {

    private final @NonNull Cart cart;
    private final @NonNull FindProducts findProducts;

    public void product(@NonNull String productCode, int quantity) {
        Product product = findProducts.byCode(new Code(productCode));
        cart.add(new CartItem(product.code().value(), product.title().value(), new Quantity(quantity)));
    }
}
