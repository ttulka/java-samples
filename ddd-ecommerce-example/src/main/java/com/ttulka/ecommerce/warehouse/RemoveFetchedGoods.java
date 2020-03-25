package com.ttulka.ecommerce.warehouse;

public interface RemoveFetchedGoods {

    /**
     * Removes all fetched goods for an order.
     *
     * @param orderId the order ID
     */
    void forOrder(OrderId orderId);
}
