package com.ttulka.samples.ddd.ecommerce.shipping.delivery.jdbc;

import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryInfo;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeliveryInfoTest {

    @Test
    void delivery_info_values() {
        DeliveryInfo deliveryInfo = new DeliveryInfoDefault(new DeliveryId(123L), new OrderId(456L));
        assertAll(
                () -> assertThat(deliveryInfo.id()).isEqualTo(new DeliveryId(123L)),
                () -> assertThat(deliveryInfo.orderId()).isEqualTo(new OrderId(456L))
        );
    }

    @Test
    void delivery_info_values_not_null() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new DeliveryInfoDefault(null, new OrderId(456L))),
                () -> assertThrows(IllegalArgumentException.class, () -> new DeliveryInfoDefault(new DeliveryId(123L), null))
        );
    }
}
