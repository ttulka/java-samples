package com.ttulka.ecommerce.sales.product.jdbc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.ttulka.ecommerce.sales.product.Code;
import com.ttulka.ecommerce.sales.product.Description;
import com.ttulka.ecommerce.sales.product.Price;
import com.ttulka.ecommerce.sales.product.Product;
import com.ttulka.ecommerce.sales.product.ProductId;
import com.ttulka.ecommerce.sales.product.Products;
import com.ttulka.ecommerce.sales.product.Title;

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

    private final @NonNull String query;
    private final @NonNull List<Object> queryParams;

    private final @NonNull JdbcTemplate jdbcTemplate;

    private final int start;
    private final int limit;

    public ProductsJdbc(@NonNull String query, @NonNull List<Object> queryParams, @NonNull JdbcTemplate jdbcTemplate) {
        this.query = query;
        this.queryParams = queryParams;
        this.jdbcTemplate = jdbcTemplate;
        this.start = 0;
        this.limit = UNLIMITED;
    }

    public ProductsJdbc(@NonNull String query, @NonNull Object queryParam, @NonNull JdbcTemplate jdbcTemplate) {
        this(query, List.of(queryParam), jdbcTemplate);
    }

    @Override
    public Products range(int start, int limit) {
        if (start < 0 || limit <= 0 || limit - start > UNLIMITED) {
            throw new IllegalArgumentException("Start must be greater than zero, " +
                    "items count must be greater than zero and less or equal than " + UNLIMITED);
        }
        return toBuilder().start(start).limit(limit).build();
    }

    @Override
    public Products range(int limit) {
        return range(0, limit);
    }

    @Override
    public Stream<Product> stream() {
        List<Object> params = new ArrayList<>(queryParams);
        params.add(start);
        params.add(limit);
        return jdbcTemplate.queryForList(query.concat(" ORDER BY 1 LIMIT ?,?"), params.toArray())
                .stream()
                .map(this::toProduct);
    }

    private Product toProduct(Map<String, Object> entry) {
        return new ProductJdbc(
                new ProductId(entry.get("id")),
                new Code((String) entry.get("code")),
                new Title((String) entry.get("title")),
                new Description((String) entry.get("description")),
                new Price(((BigDecimal) entry.get("price")).floatValue()),
                jdbcTemplate);
    }
}
