package com.ttulka.samples.ddd.ecommerce.shipping.delivery.jdbc;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.shipping.FindDeliveries;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Address;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Delivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Person;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Place;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@ContextConfiguration(classes = DeliveryJdbcConfig.class)
@Sql(statements = "INSERT INTO deliveries VALUES " +
                  "(1, 11, 'Test Person 1', 'Test Place 1', 'NEW'), " +
                  "(2, 12, 'Test Person 2', 'Test Place 2', 'PREPARED'), " +
                  "(3, 13, 'Test Person 3', 'Test Place 3', 'FETCHED'), " +
                  "(4, 14, 'Test Person 4', 'Test Place 4', 'PAID'), " +
                  "(5, 15, 'Test Person 5', 'Test Place 5', 'READY'), " +
                  "(6, 16, 'Test Person 6', 'Test Place 6', 'DISPATCHED');")
@Transactional
class DeliveryTest {

    @Autowired
    private FindDeliveries findDeliveries;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void delivery_has_values() {
        Delivery delivery = findDeliveries.byOrderId(new OrderId(11L));
        assertAll(
                () -> assertThat(delivery.id()).isEqualTo(new DeliveryId(1L)),
                () -> assertThat(delivery.orderId()).isEqualTo(new OrderId(11L)),
                () -> assertThat(delivery.address()).isEqualTo(new Address(new Person("Test Person 1"), new Place("Test Place 1"))),
                () -> assertThat(delivery.isReadyToDispatch()).isFalse(),
                () -> assertThat(delivery.isDispatched()).isFalse()
        );
    }

    @Test
    void delivery_is_found_by_order_id() {
        Delivery delivery = findDeliveries.byOrderId(new OrderId(11L));

        assertThat(delivery.id()).isEqualTo(new DeliveryId(1L));
    }

    @Test
    void delivery_is_prepared() {
        // TODO
    }

    @Test
    void delivery_can_be_prepared_only_once() {
        Delivery delivery = findDeliveries.byOrderId(new OrderId(16L));

        assertThrows(Delivery.DeliveryAlreadyPreparedException.class, () -> delivery.prepare());
    }

    @Test
    void delivery_is_ready() {
        // TODO
    }

    @Test
    void delivery_was_paid() {
        // TODO
    }

    @Test
    void delivery_was_paid_multiple_times() {
        // TODO
    }

    @Test
    void delivery_was_fetched() {
        // TODO
    }

    @Test
    void delivery_was_fetched_multiple_times() {
        // TODO
    }

    @Test
    void delivery_is_dispatched() {
        Delivery delivery = findDeliveries.byOrderId(new OrderId(15L));
        delivery.dispatch();

        assertThat(delivery.isDispatched()).isTrue();
    }

    @Test
    void delivery_is_not_ready_to_be_dispatched() {
        // TODO
    }

    @Test
    void delivery_can_be_dispatched_only_once() {
        Delivery delivery = findDeliveries.byOrderId(new OrderId(15L));
        delivery.dispatch();

        assertThrows(Delivery.DeliveryAlreadyDispatchedException.class, () -> delivery.dispatch());
    }

    @Test
    void dispatching_a_delivery_raises_an_event() {
        // TODO
    }
}
