package com.ttulka.samples.ddd.ecommerce.shipping;

public interface Deliveries {

    Delivery byOrderId(OrderId orderId);
}
