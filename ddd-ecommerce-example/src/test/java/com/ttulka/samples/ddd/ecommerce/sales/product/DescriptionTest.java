package com.ttulka.samples.ddd.ecommerce.sales.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DescriptionTest {

    private static final String STRING_50_CHARS_LONG = "01234567890123456789012345678901234567890123456789";

    @Test
    void description_created() {
        Description description = new Description(STRING_50_CHARS_LONG);
        assertThat(description.value()).isEqualTo(STRING_50_CHARS_LONG);
    }

    @Test
    void description_trimmed() {
        Description description = new Description("   " + STRING_50_CHARS_LONG + "   ");
        assertThat(description.value()).isEqualTo(STRING_50_CHARS_LONG);
    }

    @Test
    void description_fails_for_more_than_50_characters() {
        assertThatThrownBy(() -> new Description(STRING_50_CHARS_LONG + "X"));
    }
}
