package com.ttulka.samples.ddd.ecommerce.sales.category;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TitleTest {

    @Test
    void title_created() {
        Title title = new Title("test");
        assertThat(title.value()).isEqualTo("test");
    }

    @Test
    void title_trimmed() {
        Title title = new Title("   01234567890123456789   ");
        assertThat(title.value()).isEqualTo("01234567890123456789");
    }

    @Test
    void title_fails_for_a_null_value() {
        assertThatThrownBy(() -> new Title(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void title_fails_for_an_empty_string() {
        assertThatThrownBy(() -> new Title("")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void title_fails_for_more_than_20_characters() {
        assertThatThrownBy(() -> new Title("01234567890123456789X"));
    }
}
