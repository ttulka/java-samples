package com.ttulka.samples.ddd.ecommerce.sales.order;

public interface FindOrders {

    Order byId(OrderId id);
}
