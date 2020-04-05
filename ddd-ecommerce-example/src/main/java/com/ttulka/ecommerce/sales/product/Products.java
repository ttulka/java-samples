package com.ttulka.ecommerce.sales.product;

import java.util.stream.Stream;

/**
 * Products collection.
 */
public interface Products {

    enum SortBy {
        DEFAULT, TITLE, PRICE
    }

    Products sorted(SortBy by);

    Products limited(int start, int limit);

    Products limited(int limit);

    Stream<Product> stream();
}
