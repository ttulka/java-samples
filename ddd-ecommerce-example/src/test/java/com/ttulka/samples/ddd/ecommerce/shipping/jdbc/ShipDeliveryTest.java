package com.ttulka.samples.ddd.ecommerce.shipping.jdbc;

import com.ttulka.samples.ddd.ecommerce.shipping.Deliveries;
import com.ttulka.samples.ddd.ecommerce.shipping.Delivery;
import com.ttulka.samples.ddd.ecommerce.shipping.OrderId;
import com.ttulka.samples.ddd.ecommerce.shipping.ShipDelivery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = DeliveryJdbcConfig.class)
@Sql(statements = "INSERT INTO deliveries VALUES (1, 123, 'Test Person', 'Test Place', FALSE);")
@Transactional
class ShipDeliveryTest {

    @Autowired
    private Deliveries deliveries;
    @Autowired
    private ShipDelivery shipDelivery;

    @Test
    void delivery_is_shipped() {
        shipDelivery.byOrderId(new OrderId(123));

        Delivery delivery = deliveries.byOrderId(new OrderId(123));

        assertThat(delivery.isShipped()).isTrue();
    }
}
