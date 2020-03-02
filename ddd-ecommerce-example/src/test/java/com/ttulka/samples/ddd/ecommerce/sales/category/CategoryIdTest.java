package com.ttulka.samples.ddd.ecommerce.sales.category;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryIdTest {

    @Test
    void category_id_created() {
        CategoryId categoryId = new CategoryId(123L);
        assertThat(categoryId.value()).isEqualTo("123");
    }
}
