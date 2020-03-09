package com.ttulka.samples.ddd.ecommerce.catalogue;

import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.CartItem;
import com.ttulka.samples.ddd.ecommerce.sales.order.OrderItem;
import com.ttulka.samples.ddd.ecommerce.sales.order.PlaceOrder;
import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Customer;
import com.ttulka.samples.ddd.ecommerce.sales.product.Code;
import com.ttulka.samples.ddd.ecommerce.sales.product.FindProducts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class PlaceOrderFromCart {

    private final @NonNull PlaceOrder placeOrder;
    private final @NonNull FindProducts findProducts;

    public void placeOrder(@NonNull Cart cart, @NonNull Customer customer) {
        if (!cart.hasItems()) {
            throw new PlaceOrderFromCart.NoItemsToOrderException();
        }
        placeOrder.place(cart.items().stream()
                                    .map(this::toOrderItem)
                                    .collect(Collectors.toList()),
                         customer);
    }

    private OrderItem toOrderItem(CartItem cartItem) {
        return new OrderItem(
                cartItem.productCode(),
                cartItem.title(),
                productPrice(cartItem.productCode()),
                cartItem.amount().value());
    }

    private float productPrice(String productCode) {
        return findProducts.byCode(new Code(productCode))
                .price().value();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class NoItemsToOrderException extends RuntimeException {
    }
}
