package com.ttulka.samples.ddd.ecommerce.sales.order;

import java.time.Instant;
import java.util.List;

import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Address;
import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Customer;
import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Name;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderPlacedTest {

    @Test
    void when_is_set() {
        Instant now = Instant.now();
        OrderPlaced orderPlaced = new OrderPlaced(now, new FakeOrder());

        assertThat(orderPlaced.when).isEqualTo(now);
    }

    @Test
    void order_id_is_set() {
        OrderPlaced orderPlaced = new OrderPlaced(Instant.now(), new FakeOrder());

        assertThat(orderPlaced.orderId).isEqualTo(123L);
    }

    @Test
    void order_items_are_set() {
        OrderPlaced orderPlaced = new OrderPlaced(Instant.now(), new FakeOrder());
        assertAll(
                () -> assertThat(orderPlaced.orderItems).hasSize(2),
                () -> assertThat(orderPlaced.orderItems.get(0).code).isEqualTo("test-1"),
                () -> assertThat(orderPlaced.orderItems.get(0).title).isEqualTo("Test 1"),
                () -> assertThat(orderPlaced.orderItems.get(0).price).isEqualTo(1.f),
                () -> assertThat(orderPlaced.orderItems.get(0).amount).isEqualTo(1),
                () -> assertThat(orderPlaced.orderItems.get(1).code).isEqualTo("test-2"),
                () -> assertThat(orderPlaced.orderItems.get(1).title).isEqualTo("Test 2"),
                () -> assertThat(orderPlaced.orderItems.get(1).price).isEqualTo(2.f),
                () -> assertThat(orderPlaced.orderItems.get(1).amount).isEqualTo(2)
        );
    }

    @Test
    void customer_is_set() {
        OrderPlaced orderPlaced = new OrderPlaced(Instant.now(), new FakeOrder());
        assertAll(
                () -> assertThat(orderPlaced.customer.name).isEqualTo("test customer"),
                () -> assertThat(orderPlaced.customer.address).isEqualTo("test address")
        );
    }

    private static class FakeOrder implements Order {

        @Override
        public OrderId id() {
            return new OrderId(123L);
        }

        @Override
        public List<OrderItem> items() {
            return List.of(new OrderItem("test-1", "Test 1", 1.f, 1), new OrderItem("test-2", "Test 2", 2.f, 2));
        }

        @Override
        public Customer customer() {
            return new Customer(new Name("test customer"), new Address("test address"));
        }
    }
}
