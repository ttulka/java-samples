package com.ttulka.samples.ddd.ecommerce.catalogue.cart;

import com.ttulka.samples.ddd.ecommerce.sales.product.ProductId;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ItemTest {

    @Test
    void gets_attributes_as_primitives_for_the_UI() {
        Item item = new Item(
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
    void amount_is_increased() {
        Item item = new Item(
                "test-1",
                "Test",
                new Amount(123)
        );
        item.add(new Amount(321));

        assertThat(item.amount()).isEqualTo(new Amount(444));
    }
}
