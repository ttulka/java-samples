package com.ttulka.samples.ddd.ecommerce.sales.product.jdbc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.sales.category.Uri;
import com.ttulka.samples.ddd.ecommerce.sales.product.Code;
import com.ttulka.samples.ddd.ecommerce.sales.product.Description;
import com.ttulka.samples.ddd.ecommerce.sales.product.FindProducts;
import com.ttulka.samples.ddd.ecommerce.sales.product.Price;
import com.ttulka.samples.ddd.ecommerce.sales.product.Product;
import com.ttulka.samples.ddd.ecommerce.sales.product.ProductId;
import com.ttulka.samples.ddd.ecommerce.sales.product.Title;
import com.ttulka.samples.ddd.ecommerce.sales.product.UnknownProduct;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
final class ProductsJdbc implements FindProducts {

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> all() {
        return jdbcTemplate.queryForList(
                "SELECT id, code, title, description, price FROM products " +
                "ORDER BY id ASC").stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> fromCategory(@NonNull Uri categoryUri) {
        return jdbcTemplate.queryForList(
                "SELECT p.id, p.code, p.title, p.description, p.price FROM products AS p " +
                "JOIN products_in_categories AS pc ON pc.product_id = p.id " +
                "JOIN categories AS c ON c.id = pc.category_id " +
                "WHERE c.uri = ? " +
                "ORDER BY p.id ASC",
                categoryUri.value()).stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    @Override
    public Product byCode(@NonNull Code code) {
        try {
            Map<String, Object> entry = jdbcTemplate.queryForMap(
                    "SELECT id, code, title, description, price FROM products WHERE code = ?",
                    code.value());
            if (entry != null) {
                return toProduct(entry);
            }
        } catch (DataAccessException ignore) {
            log.warn("Product by code {} was not found: {}", code, ignore.getMessage());
        }
        return new UnknownProduct();
    }

    private Product toProduct(Map<String, Object> entry) {
        return new ProductJdbc(
                new ProductId(entry.get("id")),
                new Code((String) entry.get("code")),
                new Title((String) entry.get("title")),
                new Description((String) entry.get("description")),
                new Price(((BigDecimal) entry.get("price")).floatValue()),
                jdbcTemplate
        );
    }
}
