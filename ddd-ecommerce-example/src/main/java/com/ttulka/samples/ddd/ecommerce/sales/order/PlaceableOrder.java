package com.ttulka.samples.ddd.ecommerce.sales.order;

public interface PlaceableOrder extends Order {

    /**
     * Places the order.
     *
     * @throws {@link OrderAlreadyPlacedException} when the order has already been placed
     */
    void place();

    final class OrderAlreadyPlacedException extends IllegalStateException {
    }
}
