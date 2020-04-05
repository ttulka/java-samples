package com.ttulka.ecommerce.sales.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmptyProductsTest {

    @Test
    void no_items_are_returned() {
        Products products = new EmptyProducts();

        assertThat(products.stream().count()).isZero();
    }

    @Test
    void no_sorted_items_are_returned() {
        Products products = new EmptyProducts().sorted(Products.SortBy.TITLE);

        assertThat(products.stream().count()).isZero();
    }

    @Test
    void no_limited_items_are_returned() {
        Products products = new EmptyProducts().limited(0, 1);

        assertThat(products.stream().count()).isZero();
    }
}
