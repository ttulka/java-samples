package com.ttulka.samples.ddd.ecommerce.shipping;

import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Delivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.UnknownDelivery;

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
                () -> assertThat(unknownDelivery.isDispatched()).isFalse()
        );
    }

    @Test
    void prepare_noop() {
        Delivery unknownDelivery = new UnknownDelivery();
        unknownDelivery.prepare();
    }

    @Test
    void dispatch_noop() {
        Delivery unknownDelivery = new UnknownDelivery();
        unknownDelivery.dispatch();
    }
}