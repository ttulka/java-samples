package com.ttulka.samples.ddd.ecommerce.sales.order;

import java.util.Collections;
import java.util.List;

import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Address;
import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Customer;
import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Name;

import lombok.ToString;

@ToString
public final class UnknownOrder implements Order {

    @Override
    public OrderId id() {
        return new OrderId(0);
    }

    @Override
    public List<OrderItem> items() {
        return Collections.emptyList();
    }

    @Override
    public Customer customer() {
        return new Customer(
                new Name("unknown"),
                new Address("unknown"));
    }
}
