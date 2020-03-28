package com.ttulka.ecommerce.sales.order;

import java.util.List;

import com.ttulka.ecommerce.sales.order.customer.Customer;

/**
 * Order entity.
 */
public interface Order {

    OrderId id();

    List<OrderItem> items();

    Customer customer();

    final class OrderHasNoItemsException extends IllegalArgumentException {
    }
}
