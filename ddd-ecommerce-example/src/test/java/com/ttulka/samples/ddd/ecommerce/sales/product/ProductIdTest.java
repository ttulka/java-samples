package com.ttulka.samples.ddd.ecommerce.sales.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductIdTest {

    @Test
    void product_id_created() {
        ProductId productId = new ProductId(123L);
        assertThat(productId.value()).isEqualTo("123");
    }
}
