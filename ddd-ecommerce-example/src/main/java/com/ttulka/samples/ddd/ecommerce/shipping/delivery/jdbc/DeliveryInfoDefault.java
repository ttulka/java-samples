package com.ttulka.samples.ddd.ecommerce.shipping.delivery.jdbc;

import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryInfo;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(of = "deliveryId")
@RequiredArgsConstructor
@ToString
final class DeliveryInfoDefault implements DeliveryInfo {

    private final @NonNull DeliveryId deliveryId;
    private final @NonNull OrderId orderId;

    @Override
    public DeliveryId id() {
        return deliveryId;
    }

    @Override
    public OrderId orderId() {
        return orderId;
    }
}
