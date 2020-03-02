package com.ttulka.samples.ddd.ecommerce.sales.product;

public final class Description {

    private final String description;

    public Description(String description) {
        if (description != null && description.trim().length() > 50) {
            throw new IllegalArgumentException("Description cannot be longer than 50 characters!");
        }
        this.description = description.trim();
    }

    public String value() {
        return description;
    }
}
