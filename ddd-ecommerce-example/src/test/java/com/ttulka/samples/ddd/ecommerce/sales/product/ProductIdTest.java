package com.ttulka.samples.ddd.ecommerce.sales.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductIdTest {

    @Test
    void product_id_value() {
        ProductId productId = new ProductId(123L);
        assertThat(productId.value()).isEqualTo(123L);
    }

    @Test
    void fails_for_null() {
        assertThatThrownBy(() -> new ProductId(null)).isInstanceOf(IllegalArgumentException.class);
    }
}
