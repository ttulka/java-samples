package com.ttulka.ecommerce.sales.product.jdbc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ttulka.ecommerce.sales.product.Code;
import com.ttulka.ecommerce.sales.product.Description;
import com.ttulka.ecommerce.sales.product.EmptyProducts;
import com.ttulka.ecommerce.sales.product.Price;
import com.ttulka.ecommerce.sales.product.Product;
import com.ttulka.ecommerce.sales.product.ProductId;
import com.ttulka.ecommerce.sales.product.Products;
import com.ttulka.ecommerce.sales.product.Title;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Already fetched products.
 * <p>
 * To optimize the performance this object holds already fetched entries to proceed sorting or limiting upon them.
 * <p>
 * It is used for second-level sorting when the result is already limited.
 * <p>
 * For example, products are sorted by title first, the result is then limited
 * and the limited result is then sorted by price and limited again:
 * <code>
 *     products.sorted(TITLE).limit(0, 5).sorted(PRICE).limit(0, 1)
 * </code>
 * <p>
 * The second <code>sorted()</code> in the example above would not work with only SQL.
 */
@RequiredArgsConstructor
final class FetchedProductsJdbc implements Products {

    private final @NonNull List<Map<String, Object>> fetched;

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public Products sorted(SortBy by) {
        String sortBy = new SortByJdbc(by).value();
        return new FetchedProductsJdbc(
                fetched.stream()
                        .sorted((entry1, entry2) -> ((Comparable) entry1.get(sortBy))
                                .compareTo(entry2.get(sortBy)))
                        .collect(Collectors.toList()),
                jdbcTemplate);
    }

    @Override
    public Products limited(int start, int limit) {
        if (start < 0 || limit <= 0) {
            throw new IllegalArgumentException("Start must be greater than zero, " +
                    "items count must be greater than zero.");
        }
        return start < fetched.size()
                ? new FetchedProductsJdbc(
                        fetched.subList(start, Math.min(limit, fetched.size() - start)),
                        jdbcTemplate)
                : new EmptyProducts();
    }

    @Override
    public Products limited(int limit) {
        return limited(0, limit);
    }

    @Override
    public Stream<Product> stream() {
        return fetched.stream().map(this::toProduct);
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
