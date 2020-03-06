package com.ttulka.samples.ddd.ecommerce.catalogue.cart;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CartItemTest {

    @Test
    void gets_attributes_as_primitives_for_the_UI() {
        CartItem item = new CartItem(
                "test-1",
                "Test",
                new Amount(123)
        );
        assertAll(
                () -> assertThat(item.productCode()).isEqualTo("test-1"),
                () -> assertThat(item.title()).isEqualTo("Test"),
                () -> assertThat(item.amount()).isEqualTo(new Amount(123))
        );
    }

    @Test
    void item_with_increased_amount_is_returned() {
        CartItem item = new CartItem(
                "test-1",
                "Test",
                new Amount(123)
        );
        CartItem increased = item.add(new Amount(321));

        assertThat(increased.amount()).isEqualTo(new Amount(444));
    }
}
