package com.ttulka.samples.ddd.ecommerce.sales.order.jdbc;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.sales.PlaceOrder;
import com.ttulka.samples.ddd.ecommerce.sales.order.OrderItem;
import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Address;
import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Customer;
import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Name;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@JdbcTest
@ContextConfiguration(classes = OrderJdbcConfig.class)
@Transactional
class PlaceOrderTest {

    @Autowired
    private PlaceOrder placeOrder;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void order_is_placed() {
        placeOrder.place(
                List.of(new OrderItem("test", "Test", 1.f, 1)),
                new Customer(new Name("test"), new Address("test")));

        verify(eventPublisher).raise(any());
    }
}
