package com.ttulka.samples.ddd.ecommerce.shipping.delivery.jdbc;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.shipping.FindDeliveries;
import com.ttulka.samples.ddd.ecommerce.shipping.UpdateDelivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Delivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@ContextConfiguration(classes = DeliveryJdbcConfig.class)
@Sql(statements = "INSERT INTO deliveries VALUES (1, 123, 'Test Person', 'Test Place', 'PREPARED');")
@Transactional
class UpdateDeliveryTest {

    @Autowired
    private FindDeliveries findDeliveries;

    @Autowired
    private UpdateDelivery updateDelivery;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void fetched_delivery_is_not_ready_yet() {
        updateDelivery.asFetched(new OrderId(123L));

        Delivery delivery = findDeliveries.byOrderId(new OrderId(123L));
        assertAll(
                () -> assertThat(delivery.isReadyToDispatch()).isFalse(),
                () -> assertThat(delivery.isDispatched()).isFalse()
        );
    }

    @Test
    void paid_delivery_is_not_ready_yet() {
        updateDelivery.asPaid(new OrderId(123L));

        Delivery delivery = findDeliveries.byOrderId(new OrderId(123L));
        assertAll(
                () -> assertThat(delivery.isReadyToDispatch()).isFalse(),
                () -> assertThat(delivery.isDispatched()).isFalse()
        );
    }

    @Test
    void fetched_and_paid_delivery_is_ready_and_dispatched() {
        updateDelivery.asFetched(new OrderId(123L));
        updateDelivery.asPaid(new OrderId(123L));

        Delivery delivery = findDeliveries.byOrderId(new OrderId(123L));
        assertAll(
                () -> assertThat(delivery.isReadyToDispatch()).isFalse(),
                () -> assertThat(delivery.isDispatched()).isTrue()
        );
    }
}
