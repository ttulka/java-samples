package com.ttulka.samples.ddd.ecommerce.catalogue;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.CartItem;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Quantity;
import com.ttulka.samples.ddd.ecommerce.sales.FindProducts;
import com.ttulka.samples.ddd.ecommerce.sales.product.Code;
import com.ttulka.samples.ddd.ecommerce.sales.product.Product;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CartItems implements ListCartItems, AddCartItem, RemoveCartItem {

    private final @NonNull FindProducts findProducts;

    @Override
    public List<CartItem> ofCart(@NonNull Cart cart) {
        return cart.items();
    }

    @Override
    public void intoCart(@NonNull Cart cart, @NonNull String productCode, int quantity) {
        Product product = findProducts.byCode(new Code(productCode));
        cart.add(new CartItem(product.code().value(), product.title().value(), new Quantity(quantity)));
    }

    @Override
    public void fromCart(Cart cart, String productCode) {
        cart.remove(productCode);
    }
}