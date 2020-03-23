package com.ttulka.samples.ddd.ecommerce.shipping;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Address;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryItem;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;

public interface PrepareDelivery {

    /**
     * Prepares a new delivery.
     *
     * @param orderId the order ID
     * @param items   the delivery items
     * @param address the delivery address
     */
    void prepare(OrderId orderId, List<DeliveryItem> items, Address address);
}
