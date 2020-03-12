package com.ttulka.samples.ddd.ecommerce.sales.order.jdbc;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.sales.order.FindOrders;
import com.ttulka.samples.ddd.ecommerce.sales.order.Order;
import com.ttulka.samples.ddd.ecommerce.sales.order.OrderId;
import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Address;
import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Name;

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
@ContextConfiguration(classes = OrderJdbcConfig.class)
@Sql("/test-data-sales-find-orders.sql")
@Transactional
class FindOrdersTest {

    @Autowired
    private FindOrders findOrders;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void order_by_id_is_found() {
        Order order = findOrders.byId(new OrderId(1L));
        assertAll(
                () -> assertThat(order.id()).isEqualTo(new OrderId(1L)),
                () -> assertThat(order.customer().name()).isEqualTo(new Name("Test Customer 1")),
                () -> assertThat(order.customer().address()).isEqualTo(new Address("Test Address 1")),
                () -> assertThat(order.items()).hasSize(2),
                () -> assertThat(order.items().get(0).code()).isEqualTo("001"),
                () -> assertThat(order.items().get(0).title()).isEqualTo("Prod 1"),
                () -> assertThat(order.items().get(0).price()).isEqualTo(123.5f),
                () -> assertThat(order.items().get(0).quantity()).isEqualTo(1),
                () -> assertThat(order.items().get(1).code()).isEqualTo("002"),
                () -> assertThat(order.items().get(1).title()).isEqualTo("Prod 2"),
                () -> assertThat(order.items().get(1).price()).isEqualTo(321.5f),
                () -> assertThat(order.items().get(1).quantity()).isEqualTo(2)
        );
    }

    @Test
    void unknown_product_found_for_an_unknown_code() {
        Order order = findOrders.byId(new OrderId(123L));

        assertThat(order.id()).isEqualTo(new OrderId(0));
    }
}
