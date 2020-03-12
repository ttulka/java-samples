package com.ttulka.samples.ddd.ecommerce.shipping;

import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderIdTest {

    @Test
    void order_id_value() {
        OrderId orderId = new OrderId(123L);
        assertThat(orderId.value()).isEqualTo(123L);
    }

    @Test
    void fails_for_null() {
        assertThrows(IllegalArgumentException.class, () -> new OrderId(null));
    }
}
