package com.ttulka.samples.ddd.ecommerce.sales.product.jdbc;

import java.util.List;
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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
final class ProductsJdbc implements FindProducts {

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> all() {
        return jdbcTemplate.query("SELECT id, code, title, description, price FROM products " +
                                  "ORDER BY id ASC",
                                  BeanPropertyRowMapper.newInstance(ProductEntry.class)).stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> fromCategory(@NonNull Uri categoryUri) {
        return jdbcTemplate.query("SELECT p.id, p.code, p.title, p.description, p.price FROM products AS p " +
                                  "JOIN products_in_categories AS pc ON pc.product_id = p.id " +
                                  "JOIN categories AS c ON c.id = pc.category_id " +
                                  "WHERE c.uri = ? " +
                                  "ORDER BY p.id ASC",
                                  new Object[]{categoryUri.value()},
                                  BeanPropertyRowMapper.newInstance(ProductEntry.class)).stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    @Override
    public Product byCode(@NonNull Code code) {
        try {
            ProductEntry entry = jdbcTemplate.queryForObject(
                    "SELECT id, code, title, description, price FROM products " +
                    "WHERE code = ?",
                    new Object[]{code.value()},
                    BeanPropertyRowMapper.newInstance(ProductEntry.class));
            if (entry != null) {
                return toProduct(entry);
            }
        } catch (DataAccessException ignore) {
            log.warn("Product by code {} was not found: {}", code, ignore.getMessage());
        }
        return new UnknownProduct();
    }

    private Product toProduct(ProductEntry entry) {
        return new ProductJdbc(
                new ProductId(entry.id),
                new Code(entry.code),
                new Title(entry.title),
                new Description(entry.description),
                new Price(entry.price),
                jdbcTemplate
        );
    }

    @NoArgsConstructor
    @Setter
    private static class ProductEntry {

        public Long id;
        public String code;
        public String title;
        public String description;
        public float price;
    }
}
