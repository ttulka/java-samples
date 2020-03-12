package com.ttulka.samples.ddd.ecommerce.shipping.delivery.jdbc;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Address;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Quantity;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Deliveries;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Delivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryItem;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Person;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Place;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.PrepareDelivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.ProductCode;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@ContextConfiguration(classes = DeliveryJdbcConfig.class)
@Transactional
class PrepareDeliveryTest {

    @Autowired
    private Deliveries deliveries;
    @Autowired
    private PrepareDelivery prepareDelivery;

    @Test
    void delivery_for_order_is_prepared() {
        prepareDelivery.forOrder(
                new OrderId(123L),
                List.of(new DeliveryItem(new ProductCode("test"), new Quantity(1))),
                new Address(new Person("test"), new Place("test")));

        Delivery delivery = deliveries.byOrderId(new OrderId(123L));

        assertAll(
                () -> assertThat(delivery.orderId()).isEqualTo(new OrderId(123L)),
                () -> assertThat(delivery.items()).containsExactly(new DeliveryItem(new ProductCode("test"), new Quantity(1))),
                () -> assertThat(delivery.address()).isEqualTo(new Address(new Person("test"), new Place("test")))
        );
    }
}
