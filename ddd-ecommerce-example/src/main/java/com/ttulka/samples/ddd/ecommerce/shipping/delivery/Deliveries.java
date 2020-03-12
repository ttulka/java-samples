package com.ttulka.samples.ddd.ecommerce.shipping.delivery;

public interface Deliveries {

    Delivery byOrderId(OrderId orderId);
}
