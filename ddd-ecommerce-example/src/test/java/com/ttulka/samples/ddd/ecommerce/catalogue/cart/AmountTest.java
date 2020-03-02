package com.ttulka.samples.ddd.ecommerce.catalogue.cart;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AmountTest {

    @Test
    void amount_created() {
        Amount amount = new Amount(123);
        assertThat(amount.value()).isEqualTo(123);
    }

    @Test
    void amount_fails_for_a_value_less_than_one() {
        assertThatThrownBy(() -> new Amount(0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Amount(-1)).isInstanceOf(IllegalArgumentException.class);
    }
}
