package com.ttulka.samples.ddd.ecommerce.shipping.delivery.jdbc;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.shipping.FindDeliveries;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Address;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Delivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryInfo;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryItem;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Person;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Place;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.ProductCode;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Quantity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@ContextConfiguration(classes = DeliveryJdbcConfig.class)
@Sql("/test-data-shipping-find-deliveries.sql")
class FindDeliveriesTest {

    @Autowired
    private FindDeliveries findDeliveries;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void all_deliveries_are_found() {
        List<DeliveryInfo> deliveries = findDeliveries.all();
        assertAll(
                () -> assertThat(deliveries).hasSize(2),
                () -> assertThat(deliveries.get(0).id()).isEqualTo(new DeliveryId(301L)),
                () -> assertThat(deliveries.get(0).orderId()).isEqualTo(new OrderId(3001L)),
                () -> assertThat(deliveries.get(1).id()).isEqualTo(new DeliveryId(302L)),
                () -> assertThat(deliveries.get(1).orderId()).isEqualTo(new OrderId(3002L))
        );
    }

    @Test
    void delivery_is_found_by_order_id() {
        Delivery delivery = findDeliveries.byOrderId(new OrderId(3001L));
        assertAll(
                () -> assertThat(delivery.id()).isEqualTo(new DeliveryId(301L)),
                () -> assertThat(delivery.orderId()).isEqualTo(new OrderId(3001L)),
                () -> assertThat(delivery.address()).isEqualTo(new Address(new Person("Person 1"), new Place("Place 1"))),
                () -> assertThat(delivery.items()).containsExactly(new DeliveryItem(new ProductCode("test-1"), new Quantity(111))),
                () -> assertThat(delivery.isReadyToDispatch()).isTrue(),
                () -> assertThat(delivery.isDispatched()).isFalse()
        );
    }

    @Test
    void delivery_is_not_found_by_order_id() {
        Delivery delivery = findDeliveries.byOrderId(new OrderId(888L));

        assertThat(delivery.id()).isEqualTo(new DeliveryId(0));
    }

    @Test
    void status_is_merged_with_events_ledger() {
        Delivery delivery = findDeliveries.byOrderId(new OrderId(3002L));

        assertThat(delivery.isDispatched()).isTrue();
    }
}
