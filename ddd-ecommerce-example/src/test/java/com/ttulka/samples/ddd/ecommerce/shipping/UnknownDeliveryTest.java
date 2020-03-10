package com.ttulka.samples.ddd.ecommerce.shipping;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UnknownDeliveryTest {

    @Test
    void unknown_delivery_has_values() {
        Delivery unknownDelivery = new UnknownDelivery();
        assertAll(
                () -> assertThat(unknownDelivery.id()).isEqualTo(new DeliveryId(0)),
                () -> assertThat(unknownDelivery.orderId()).isEqualTo(new OrderId(0)),
                () -> assertThat(unknownDelivery.items()).hasSize(0),
                () -> assertThat(unknownDelivery.address()).isNotNull(),
                () -> assertThat(unknownDelivery.isShipped()).isFalse()
        );
    }

    @Test
    void prepare_noop() {
        Delivery unknownDelivery = new UnknownDelivery();
        unknownDelivery.prepare();
    }

    @Test
    void ship_noop() {
        Delivery unknownDelivery = new UnknownDelivery();
        unknownDelivery.ship();
    }
}
