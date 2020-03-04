package com.ttulka.samples.ddd.ecommerce.sales.category;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class UnknownCategoryTest {

    @Test
    void unknown_category_has_values() {
        Category unknownCategory = new UnknownCategory();
        assertAll(
                () -> assertThat(unknownCategory.id()).isEqualTo(new CategoryId(0)),
                () -> assertThat(unknownCategory.uri()).isNotNull(),
                () -> assertThat(unknownCategory.title()).isNotNull()
        );
    }

    @Test
    void change_title_fails() {
        Category unknownCategory = new UnknownCategory();
        assertThatThrownBy(() -> unknownCategory.changeTitle(new Title("test")));
    }
}
