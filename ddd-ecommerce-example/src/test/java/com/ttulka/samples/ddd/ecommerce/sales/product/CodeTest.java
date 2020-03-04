package com.ttulka.samples.ddd.ecommerce.sales.product;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CodeTest {

    private static final String STRING_50_CHARS_LONG = "01234567890123456789012345678901234567890123456789";

    @Test
    void code_value() {
        String uuid = UUID.randomUUID().toString();
        Code code = new Code(uuid);
        assertThat(code.value()).isEqualTo(uuid);
    }

    @Test
    void code_value_is_trimmed() {
        String uuid = UUID.randomUUID().toString();
        Code code = new Code("   " + uuid + "   ");
        assertThat(code.value()).isEqualTo(uuid);
    }

    @Test
    void code_fails_for_a_null_value() {
        assertThatThrownBy(() -> new Code(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void code_fails_for_an_empty_string() {
        assertThatThrownBy(() -> new Code("")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void code_fails_for_more_than_50_characters() {
        assertThatThrownBy(() -> new Code(STRING_50_CHARS_LONG + "X")).isInstanceOf(IllegalArgumentException.class);
    }
}
