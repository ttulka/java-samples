package com.ttulka.samples.ddd.ecommerce.shipping;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Address;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryItem;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;

public interface PrepareDelivery {

    void prepare(OrderId orderId, List<DeliveryItem> items, Address address);
}
