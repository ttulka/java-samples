package com.ttulka.samples.ddd.ecommerce.shipping.delivery;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(of = "deliveryId")
@RequiredArgsConstructor
@ToString
public final class DeliveryInfo {

    private final @NonNull DeliveryId deliveryId;
    private final @NonNull OrderId orderId;

    public DeliveryId id() {
        return deliveryId;
    }

    public OrderId orderId() {
        return orderId;
    }
}
