package com.ttulka.samples.ddd.ecommerce.sales;

import com.ttulka.samples.ddd.ecommerce.sales.order.Order;
import com.ttulka.samples.ddd.ecommerce.sales.order.OrderId;

public interface FindOrders {

    Order byId(OrderId id);
}
