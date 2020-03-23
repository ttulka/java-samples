package com.ttulka.samples.ddd.ecommerce.shipping;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Delivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryInfo;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;

public interface FindDeliveries {

    /**
     * Lists all deliveries.
     *
     * @return all deliveries
     */
    List<DeliveryInfo> all();

    /**
     * Finds a delivery by the order ID.
     *
     * @param orderId the order ID
     * @return the delivery
     */
    Delivery byOrderId(OrderId orderId);
}
