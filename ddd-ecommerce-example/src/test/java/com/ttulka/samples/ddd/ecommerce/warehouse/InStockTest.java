package com.ttulka.samples.ddd.ecommerce.warehouse;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InStockTest {

    @Test
    void in_stock_value() {
        InStock inStock = new InStock(123);
        assertThat(inStock.amount()).isEqualTo(123);
    }

    @Test
    void in_stock_zero_value() {
        InStock inStock = new InStock(0);
        assertThat(inStock.amount()).isEqualTo(0);
    }

    @Test
    void in_stock_fails_for_a_value_less_than_zero() {
        assertThrows(IllegalArgumentException.class, () -> new InStock(-1));
    }

    @Test
    void in_stock_is_added() {
        InStock sum = new InStock(1).add(new InStock(2));
        assertThat(sum).isEqualTo(new InStock(3));
    }
}
