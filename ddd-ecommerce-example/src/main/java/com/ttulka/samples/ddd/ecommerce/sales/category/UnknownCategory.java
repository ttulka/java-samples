package com.ttulka.samples.ddd.ecommerce.sales.category;

import lombok.ToString;

@ToString
public final class UnknownCategory implements Category {

    @Override
    public CategoryId id() {
        return new CategoryId(-1L);
    }

    @Override
    public Uri uri() {
        return new Uri("unknown");
    }

    @Override
    public Title title() {
        return new Title("unknown category");
    }

    @Override
    public void changeTitle(Title title) {
        throw new UnsupportedOperationException("Cannot change the title: unknown category.");
    }
}