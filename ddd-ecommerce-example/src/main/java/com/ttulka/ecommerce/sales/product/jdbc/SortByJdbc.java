package com.ttulka.ecommerce.sales.product.jdbc;

import com.ttulka.ecommerce.sales.product.Products;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * JDBC mapping for sorted by product columns.
 */
@RequiredArgsConstructor
final class SortByJdbc {

    private final @NonNull Products.SortBy sortBy;

    public String value() {
        switch (sortBy) {
            case TITLE:
                return "title";
            case PRICE:
                return "price";
            default:
                return "id";
        }
    }
}
