package com.ttulka.samples.ddd.ecommerce.catalogue.cart;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "productCode")
@ToString
@Builder(toBuilder = true, access = AccessLevel.PACKAGE)
public final class CartItem {

    private final String productCode;
    private final String title;
    private final Amount amount;

    public String productCode() {
        return productCode;
    }

    public String title() {
        return title;
    }

    public Amount amount() {
        return amount;
    }

    public CartItem add(Amount addend) {
        return toBuilder()
                .amount(amount.add(addend))
                .build();
    }
}
