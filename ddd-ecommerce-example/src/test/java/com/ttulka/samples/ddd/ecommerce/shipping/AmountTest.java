package com.ttulka.samples.ddd.ecommerce.shipping;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AmountTest {

    @Test
    void amount_value() {
        Amount amount = new Amount(123);
        assertThat(amount.value()).isEqualTo(123);
    }

    @Test
    void amount_fails_for_a_value_less_than_zero() {
        assertThrows(IllegalArgumentException.class, () -> new Amount(-1));
    }

    @Test
    void amount_is_added() {
        Amount sum = new Amount(1).add(new Amount(2));
        assertThat(sum).isEqualTo(new Amount(3));
    }
}
