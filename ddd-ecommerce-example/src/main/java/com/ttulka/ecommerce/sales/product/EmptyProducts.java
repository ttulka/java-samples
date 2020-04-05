package com.ttulka.ecommerce.sales.product;

import java.util.stream.Stream;

/**
 * Null object implementation for Products collection.
 */
public final class EmptyProducts implements Products {

    @Override
    public Products sorted(SortBy by) {
        return new EmptyProducts();
    }

    @Override
    public Products limited(int start, int limit) {
        return new EmptyProducts();
    }

    @Override
    public Products limited(int limit) {
        return limited(0, limit);
    }

    @Override
    public Stream<Product> stream() {
        return Stream.empty();
    }
}
