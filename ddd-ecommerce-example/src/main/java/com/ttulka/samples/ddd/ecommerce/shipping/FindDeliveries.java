package com.ttulka.samples.ddd.ecommerce.shipping;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Delivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryInfo;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;

public interface FindDeliveries {

    List<DeliveryInfo> all();

    Delivery byOrderId(OrderId orderId);
}
