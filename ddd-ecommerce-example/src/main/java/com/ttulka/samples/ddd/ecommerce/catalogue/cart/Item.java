package com.ttulka.samples.ddd.ecommerce.catalogue.cart;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode(of = "productCode")
@ToString
public final class Item {

    private final String productCode;
    private final String title;
    private Amount amount;

    public String productCode() {
        return productCode;
    }

    public String title() {
        return title;
    }

    public Amount amount() {
        return amount;
    }

    public void add(Amount addend) {
        amount = amount.add(addend);
    }
}
