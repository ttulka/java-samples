package com.ttulka.samples.ddd.ecommerce.sales.category;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UriTest {

    @Test
    void uri_value() {
        Uri uri = new Uri("test");
        assertThat(uri.value()).isEqualTo("test");
    }

    @Test
    void uri_value_is_trimmed() {
        Uri uri = new Uri("   01234567890123456789   ");
        assertThat(uri.value()).isEqualTo("01234567890123456789");
    }

    @Test
    void uri_fails_for_a_null_value() {
        assertThatThrownBy(() -> new Uri(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void uri_fails_for_an_empty_string() {
        assertThatThrownBy(() -> new Uri("")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void uri_fails_for_more_than_20_characters() {
        assertThatThrownBy(() -> new Uri("01234567890123456789X"));
    }
}
