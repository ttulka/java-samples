package com.ttulka.ecommerce.shipping.delivery;

import java.util.List;

public interface Delivery {

    DeliveryId id();

    OrderId orderId();

    List<DeliveryItem> items();

    Address address();

    /**
     * @throws {@link DeliveryAlreadyPreparedException} when already prepared.
     */
    void prepare();

    void markAsFetched();

    void markAsPaid();

    /**
     * @throws {@link DeliveryNotReadyToBeDispatchedException} when not ready to be dispatched.
     * @throws {@link DeliveryAlreadyDispatchedException} when already dispatched.
     */
    void dispatch();

    boolean isDispatched();

    boolean isReadyToDispatch();

    enum Status {
        NEW, PREPARED, PAID, FETCHED, READY, DISPATCHED
    }

    final class DeliveryAlreadyPreparedException extends IllegalStateException {
    }

    final class DeliveryNotReadyToBeDispatchedException extends IllegalStateException {
    }

    final class DeliveryAlreadyDispatchedException extends IllegalStateException {
    }
}
