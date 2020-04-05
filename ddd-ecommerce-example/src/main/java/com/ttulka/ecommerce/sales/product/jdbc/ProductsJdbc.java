package com.ttulka.ecommerce.sales.product.jdbc;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.ttulka.ecommerce.common.jdbc.JdbcEntries;
import com.ttulka.ecommerce.sales.product.Product;
import com.ttulka.ecommerce.sales.product.Products;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * JDBC implementation of the products collection.
 */
@Builder(access = AccessLevel.PRIVATE, toBuilder = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class ProductsJdbc implements Products {

    private static final int UNLIMITED = 1000;

    private final @NonNull JdbcEntries entries;

    private final @NonNull JdbcTemplate jdbcTemplate;

    private final SortBy sortBy;
    private final int start;
    private final int limit;

    public ProductsJdbc(@NonNull String query, @NonNull List<Object> queryParams, @NonNull JdbcTemplate jdbcTemplate) {
        this.entries = new JdbcEntries(query, queryParams, jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
        this.sortBy = SortBy.DEFAULT;
        this.start = 0;
        this.limit = UNLIMITED;
    }

    public ProductsJdbc(@NonNull String query, @NonNull Object queryParam, @NonNull JdbcTemplate jdbcTemplate) {
        this(query, List.of(queryParam), jdbcTemplate);
    }

    @Override
    public Products sorted(SortBy by) {
        return toBuilder().sortBy(by).build();
    }

    @Override
    public Products limited(int start, int limit) {
        if (start < 0 || limit <= 0 || limit - start > UNLIMITED) {
            throw new IllegalArgumentException("Start must be greater than zero, " +
                    "items count must be greater than zero and less or equal than " + UNLIMITED);
        }
        return isLimited()
                ? new FetchedProductsJdbc(fetched(), jdbcTemplate)
                : toBuilder().start(start).limit(limit).build();
    }

    @Override
    public Products limited(int limit) {
        return limited(0, limit);
    }

    private List<Map<String, Object>> fetched() {
        return entries
                .sorted(new SortByJdbc(sortBy).value())
                .limited(start, limit)
                .list();
    }

    private boolean isLimited() {
        return start > 0 || limit != UNLIMITED;
    }

    @Override
    public Stream<Product> stream() {
        return new FetchedProductsJdbc(fetched(), jdbcTemplate).stream();
    }
}
