package com.ttulka.ecommerce.sales;

import java.util.List;

import com.ttulka.ecommerce.sales.order.OrderItem;
import com.ttulka.ecommerce.sales.order.customer.Customer;

public interface PlaceOrder {

    /**
     * Places a new order.
     *
     * @param items    the order items
     * @param customer the customer
     */
    void place(List<OrderItem> items, Customer customer);
}
