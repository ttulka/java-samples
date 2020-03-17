package com.ttulka.samples.ddd.ecommerce.shipping.delivery;

public interface DeliveryInfo {

    DeliveryId id();

    OrderId orderId();
}
