package com.ttulka.samples.ddd.ecommerce.warehouse;

import java.util.Collection;

public interface FetchGoods {

    void fromOrder(OrderId orderId, Collection<ToFetch> toFetch);
}
