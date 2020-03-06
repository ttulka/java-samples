package com.ttulka.samples.ddd.ecommerce.catalogue.cart;

import com.ttulka.samples.ddd.ecommerce.catalogue.cart.cookies.CartCookies;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CartCookiesTest {

    @Test
    void cart_is_empty() {
        Cart cart = new CartCookies(new MockHttpServletRequest(), new MockHttpServletResponse());
        assertThat(cart.items()).isEmpty();
    }

    @Test
    void item_is_added() {
        Cart cart = new CartCookies(new MockHttpServletRequest(), new MockHttpServletResponse());
        cart.add(new CartItem("test-1", "Test 1", new Amount(123)));
        assertAll(
                () -> assertThat(cart.items()).hasSize(1),
                () -> assertThat(cart.items().get(0).productCode()).isEqualTo("test-1"),
                () -> assertThat(cart.items().get(0).title()).isEqualTo("Test 1"),
                () -> assertThat(cart.items().get(0).amount()).isEqualTo(new Amount(123))
        );
    }

    @Test
    void amount_is_increased() {
        Cart cart = new CartCookies(new MockHttpServletRequest(), new MockHttpServletResponse());
        cart.add(new CartItem("test-1", "Test 1", new Amount(123)));
        cart.add(new CartItem("test-1", "Test 1", new Amount(321)));
        assertAll(
                () -> assertThat(cart.items()).hasSize(1),
                () -> assertThat(cart.items().get(0).productCode()).isEqualTo("test-1"),
                () -> assertThat(cart.items().get(0).title()).isEqualTo("Test 1"),
                () -> assertThat(cart.items().get(0).amount()).isEqualTo(new Amount(444))
        );
    }

    @Test
    void multiple_items_are_added() {
        Cart cart = new CartCookies(new MockHttpServletRequest(), new MockHttpServletResponse());
        cart.add(new CartItem("test-1", "Test 1", new Amount(123)));
        cart.add(new CartItem("test-2", "Test 2", new Amount(321)));
        assertAll(
                () -> assertThat(cart.items()).hasSize(2),
                () -> assertThat(cart.items().get(0).productCode()).isEqualTo("test-1"),
                () -> assertThat(cart.items().get(0).title()).isEqualTo("Test 1"),
                () -> assertThat(cart.items().get(0).amount()).isEqualTo(new Amount(123)),
                () -> assertThat(cart.items().get(1).productCode()).isEqualTo("test-2"),
                () -> assertThat(cart.items().get(1).title()).isEqualTo("Test 2"),
                () -> assertThat(cart.items().get(1).amount()).isEqualTo(new Amount(321))
        );
    }

    @Test
    void item_is_removed() {
        Cart cart = new CartCookies(new MockHttpServletRequest(), new MockHttpServletResponse());
        cart.add(new CartItem("test-1", "Test 1", new Amount(123)));
        cart.add(new CartItem("test-2", "Test 2", new Amount(321)));

        cart.remove("test-1");
        assertAll(
                () -> assertThat(cart.items()).hasSize(1),
                () -> assertThat(cart.items().get(0).productCode()).isEqualTo("test-2"),
                () -> assertThat(cart.items().get(0).title()).isEqualTo("Test 2"),
                () -> assertThat(cart.items().get(0).amount()).isEqualTo(new Amount(321))
        );
    }

    @Test
    void cart_is_emptied() {
        Cart cart = new CartCookies(new MockHttpServletRequest(), new MockHttpServletResponse());
        cart.add(new CartItem("test-1", "Test 1", new Amount(123)));
        cart.add(new CartItem("test-2", "Test 2", new Amount(321)));
        cart.empty();

        assertThat(cart.items()).isEmpty();
    }
}
