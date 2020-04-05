package com.ttulka.ecommerce.sales.product.jdbc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import com.ttulka.ecommerce.sales.FindProducts;
import com.ttulka.ecommerce.sales.category.Uri;
import com.ttulka.ecommerce.sales.product.Code;
import com.ttulka.ecommerce.sales.product.Description;
import com.ttulka.ecommerce.sales.product.Price;
import com.ttulka.ecommerce.sales.product.Product;
import com.ttulka.ecommerce.sales.product.ProductId;
import com.ttulka.ecommerce.sales.product.Products;
import com.ttulka.ecommerce.sales.product.Title;
import com.ttulka.ecommerce.sales.product.UnknownProduct;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for Product use-cases.
 */
@RequiredArgsConstructor
@Slf4j
final class FindProductsJdbc implements FindProducts {

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public Products all() {
        return new ProductsJdbc(
                "SELECT id, code, title, description, price FROM products",
                Collections.emptyList(), jdbcTemplate);
    }

    @Override
    public Products fromCategory(@NonNull Uri categoryUri) {
        return new ProductsJdbc(
                "SELECT p.id, p.code, p.title, p.description, p.price FROM products AS p " +
                        "JOIN products_in_categories AS pc ON pc.product_id = p.id " +
                        "JOIN categories AS c ON c.id = pc.category_id " +
                        "WHERE c.uri = ?",
                categoryUri.value(), jdbcTemplate);
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
            log.warn("Product by code {} was not found.", code);
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
