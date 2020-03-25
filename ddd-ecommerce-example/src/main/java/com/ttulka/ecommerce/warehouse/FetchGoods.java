package com.ttulka.ecommerce.warehouse;

import java.util.Collection;

public interface FetchGoods {

    /**
     * Fetches goods for an order.
     *
     * @param orderId the order ID
     * @param toFetch the goods to fetch
     */
    void fromOrder(OrderId orderId, Collection<ToFetch> toFetch);
}
