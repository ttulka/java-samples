package com.ttulka.samples.ddd.ecommerce.sales.category;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoryIdTest {

    @Test
    void category_id_value() {
        CategoryId categoryId = new CategoryId(123L);
        assertThat(categoryId.value()).isEqualTo(123L);
    }

    @Test
    void fails_for_null() {
        assertThrows(IllegalArgumentException.class, () -> new CategoryId(null));
    }
}
