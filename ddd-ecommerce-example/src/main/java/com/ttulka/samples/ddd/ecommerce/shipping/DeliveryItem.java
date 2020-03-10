package com.ttulka.samples.ddd.ecommerce.shipping;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public final class DeliveryItem {

    private final @NonNull ProductCode productCode;
    private final @NonNull Amount amount;

    public ProductCode productCode() {
        return productCode;
    }

    public Amount amount() {
        return amount;
    }
}
