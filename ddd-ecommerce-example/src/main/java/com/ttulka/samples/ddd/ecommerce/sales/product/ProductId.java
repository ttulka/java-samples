package com.ttulka.samples.ddd.ecommerce.sales.product;

import java.util.UUID;

public final class ProductId {

    private final UUID id;

    public ProductId() {
        this.id = UUID.randomUUID();
    }

    public ProductId(String id) {
        this.id = UUID.fromString(id);
    }

    public String value() {
        return id.toString();
    }
}
