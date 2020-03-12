package com.ttulka.samples.ddd.ecommerce.shipping.jdbc;

import com.ttulka.samples.ddd.ecommerce.shipping.Address;
import com.ttulka.samples.ddd.ecommerce.shipping.Deliveries;
import com.ttulka.samples.ddd.ecommerce.shipping.Delivery;
import com.ttulka.samples.ddd.ecommerce.shipping.DeliveryId;
import com.ttulka.samples.ddd.ecommerce.shipping.OrderId;
import com.ttulka.samples.ddd.ecommerce.shipping.Person;
import com.ttulka.samples.ddd.ecommerce.shipping.Place;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@ContextConfiguration(classes = DeliveryJdbcConfig.class)
@Sql(statements = "INSERT INTO deliveries VALUES (1, 123, 'Test Person', 'Test Place', FALSE);")
@Transactional
class DeliveryTest {

    @Autowired
    private Deliveries deliveries;

    @Test
    void delivery_has_values() {
        Delivery delivery = deliveries.byOrderId(new OrderId(123L));
        assertAll(
                () -> assertThat(delivery.id()).isEqualTo(new DeliveryId(1L)),
                () -> assertThat(delivery.orderId()).isEqualTo(new OrderId(123L)),
                () -> assertThat(delivery.address()).isEqualTo(new Address(new Person("Test Person"), new Place("Test Place"))),
                () -> assertThat(delivery.isDispatched()).isFalse()
        );
    }

    @Test
    void delivery_is_found_by_order_id() {
        Delivery delivery = deliveries.byOrderId(new OrderId(123L));

        assertThat(delivery.id()).isEqualTo(new DeliveryId(1L));
    }

    @Test
    void delivery_is_already_prepared() {
        Delivery delivery = deliveries.byOrderId(new OrderId(123L));

        assertThrows(Delivery.DeliveryAlreadyPreparedException.class, () -> delivery.prepare());
    }

    @Test
    void delivery_is_dispatched() {
        Delivery delivery = deliveries.byOrderId(new OrderId(123L));
        delivery.dispatch();

        assertThat(delivery.isDispatched()).isTrue();
    }

    @Test
    void delivery_can_be_dispatched_only_once() {
        Delivery delivery = deliveries.byOrderId(new OrderId(123L));
        delivery.dispatch();

        assertThrows(Delivery.DeliveryAlreadyDispatchedException.class, () -> delivery.dispatch());
    }
}
