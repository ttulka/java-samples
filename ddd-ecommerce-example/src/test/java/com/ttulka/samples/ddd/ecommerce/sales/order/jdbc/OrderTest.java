package com.ttulka.samples.ddd.ecommerce.sales.order.jdbc;

import java.util.Collections;
import java.util.List;

import com.ttulka.samples.ddd.ecommerce.MockEventPublisher;
import com.ttulka.samples.ddd.ecommerce.sales.order.Order;
import com.ttulka.samples.ddd.ecommerce.sales.order.OrderItem;
import com.ttulka.samples.ddd.ecommerce.sales.order.PlaceableOrder;
import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Address;
import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Customer;
import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Name;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@Transactional
class OrderTest {

    @Test
    void items_are_returned() {
        Order order = new OrderJdbc(
                List.of(new OrderItem("test-1", "Test 1", 1.f, 1), new OrderItem("test-2", "Test 2", 2.f, 2)),
                new Customer(new Name("test"), new Address("test")),
                new MockEventPublisher());
        assertAll(
                () -> assertThat(order.items()).hasSize(2),
                () -> assertThat(order.items().get(0).code()).isEqualTo("test-1"),
                () -> assertThat(order.items().get(0).title()).isEqualTo("Test 1"),
                () -> assertThat(order.items().get(0).price()).isEqualTo(1.f),
                () -> assertThat(order.items().get(0).amount()).isEqualTo(1),
                () -> assertThat(order.items().get(1).code()).isEqualTo("test-2"),
                () -> assertThat(order.items().get(1).title()).isEqualTo("Test 2"),
                () -> assertThat(order.items().get(1).price()).isEqualTo(2.f),
                () -> assertThat(order.items().get(1).amount()).isEqualTo(2)
        );
    }

    @Test
    void customer_is_returned() {
        Order order = new OrderJdbc(
                List.of(new OrderItem("test", "Test", 1.f, 1)),
                new Customer(new Name("test name"), new Address("test address")),
                new MockEventPublisher());
        assertAll(
                () -> assertThat(order.customer()).isNotNull(),
                () -> assertThat(order.customer().name()).isEqualTo(new Name("test name")),
                () -> assertThat(order.customer().address()).isEqualTo(new Address("test address"))
        );
    }

    @Test
    void order_contains_at_least_one_item() {
        assertThrows(Order.OrderHasNoItemsException.class,
                     () -> new OrderJdbc(
                             Collections.emptyList(),
                             new Customer(new Name("test"), new Address("test")),
                             new MockEventPublisher()));
    }

    @Test
    void placed_order_raises_an_event() {
        MockEventPublisher mockEventPublisher = new MockEventPublisher();
        PlaceableOrder order = new OrderJdbc(
                List.of(new OrderItem("test", "Test", 1.f, 1)),
                new Customer(new Name("test"), new Address("test")),
                mockEventPublisher);
        order.place();

        assertThat(mockEventPublisher.events()).hasSize(1);
    }

    @Test
    void order_can_be_placed_only_once() {
        MockEventPublisher mockEventPublisher = new MockEventPublisher();
        PlaceableOrder order = new OrderJdbc(
                List.of(new OrderItem("test", "Test", 1.f, 1)),
                new Customer(new Name("test"), new Address("test")),
                mockEventPublisher);
        order.place();

        assertAll(
                () -> assertThrows(PlaceableOrder.OrderAlreadyPlacedException.class, () -> order.place()),
                () -> assertThat(mockEventPublisher.events()).hasSize(1)    // not two events
        );
    }
}
