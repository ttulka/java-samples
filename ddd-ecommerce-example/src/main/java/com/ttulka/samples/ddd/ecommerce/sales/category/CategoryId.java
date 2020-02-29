package com.ttulka.samples.ddd.ecommerce.sales.category;

public final class CategoryId {

    private final String id;

    public CategoryId(String id) {
        this.id = id;
    }

    public String value() {
        return id;
    }
}
