package com.ttulka.samples.ddd.ecommerce.sales.order;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.sales.order.customer.Customer;

public interface Order {

    List<OrderItem> items();

    Customer customer();

    final class OrderHasNoItemsException extends IllegalArgumentException {
    }
}
