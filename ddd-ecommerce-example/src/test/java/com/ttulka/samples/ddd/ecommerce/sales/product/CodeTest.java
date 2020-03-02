package com.ttulka.samples.ddd.ecommerce.sales.product;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CodeTest {

    @Test
    void code_created() {
        String uuid = UUID.randomUUID().toString();
        Code code = new Code(uuid);
        assertThat(code.value()).isEqualTo(uuid);
    }

    @Test
    void code_trimmed() {
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
    void code_fails_for_a_wrong_format() {
        assertThatThrownBy(() -> new Code("wrong"));
    }
}
