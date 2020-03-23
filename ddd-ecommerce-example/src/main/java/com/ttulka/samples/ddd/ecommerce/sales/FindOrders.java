package com.ttulka.samples.ddd.ecommerce.sales;

import com.ttulka.samples.ddd.ecommerce.sales.order.Order;
import com.ttulka.samples.ddd.ecommerce.sales.order.OrderId;

public interface FindOrders {

    /**
     * Finds an order by the order ID.
     *
     * @param id the order ID
     * @return the order
     */
    Order byId(OrderId id);
}
