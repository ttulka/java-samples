package com.ttulka.samples.ddd.ecommerce.sales.order;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class OrderItem {

    private final String code;
    private final String title;
    private final Float price;
    private final Integer amount;

    public OrderItem(@NonNull String code, @NonNull String title, @NonNull Float price, @NonNull Integer amount) {
        if (code.isBlank()) {
            throw new IllegalArgumentException("Code cannot be empty!");
        }
        if (title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty!");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be less than zero!");
        }
        if (amount < 1) {
            throw new IllegalArgumentException("Amount cannot be less than one!");
        }
        this.code = code;
        this.title = title;
        this.price = price;
        this.amount = amount;
    }
}
