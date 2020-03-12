package com.ttulka.samples.ddd.ecommerce.shipping.delivery;

import java.util.List;

public interface PrepareDelivery {

    void forOrder(OrderId orderId, List<DeliveryItem> items, Address address);
}
