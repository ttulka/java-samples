package com.ttulka.samples.ddd.ecommerce.sales.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {

    @Test
    void price_value() {
        Price price = new Price(12.34f);
        assertThat(price.value()).isEqualTo(12.34f);
    }

    @Test
    void price_created_for_a_zero_value() {
        Price price = new Price(0.f);
        assertThat(price.value()).isEqualTo(0.f);
    }

    @Test
    void price_fails_for_a_negative_value() {
        assertThatThrownBy(() -> new Price(-12.34f));
    }
}
