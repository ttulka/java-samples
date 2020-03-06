package com.ttulka.samples.ddd.ecommerce.catalogue;

import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Amount;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.cookies.CartCookies;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.CartItem;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ListCartTest {

    @Test
    void item_is_listed() {
        Cart cart = new CartCookies(new MockHttpServletRequest(), new MockHttpServletResponse());
        ListCart listCart = new ListCart(cart);

        cart.add(new CartItem("test-1", "Test 1", new Amount(123)));
        assertAll(
                () -> assertThat(listCart.items()).hasSize(1),
                () -> assertThat(listCart.items().get(0).productCode()).isEqualTo("test-1"),
                () -> assertThat(listCart.items().get(0).title()).isEqualTo("Test 1"),
                () -> assertThat(listCart.items().get(0).amount()).isEqualTo(new Amount(123))
        );
    }

    @Test
    void multiple_items_are_listed() {
        Cart cart = new CartCookies(new MockHttpServletRequest(), new MockHttpServletResponse());
        ListCart listCart = new ListCart(cart);

        cart.add(new CartItem("test-1", "Test 1", new Amount(123)));
        cart.add(new CartItem("test-2", "Test 2", new Amount(321)));
        assertAll(
                () -> assertThat(listCart.items()).hasSize(2),
                () -> assertThat(listCart.items().get(0).productCode()).isEqualTo("test-1"),
                () -> assertThat(listCart.items().get(0).title()).isEqualTo("Test 1"),
                () -> assertThat(listCart.items().get(0).amount()).isEqualTo(new Amount(123)),
                () -> assertThat(listCart.items().get(1).productCode()).isEqualTo("test-2"),
                () -> assertThat(listCart.items().get(1).title()).isEqualTo("Test 2"),
                () -> assertThat(listCart.items().get(1).amount()).isEqualTo(new Amount(321))
        );
    }
}
