package com.ttulka.samples.ddd.ecommerce.sales.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
final class ProductsJdbc implements Products {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> all() {
        return jdbcTemplate.query("SELECT * FROM products",
                                  BeanPropertyRowMapper.newInstance(ProductEntry.class)).stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> inCategory(String categoryId) {
        return jdbcTemplate.query("SELECT p.* FROM products AS p " +
                                  "JOIN products_in_categories AS pc ON pc.product_id = p.id " +
                                  "WHERE pc.category_id = ? ",
                                  new Object[]{categoryId},
                                  BeanPropertyRowMapper.newInstance(ProductEntry.class)).stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    private Product toProduct(ProductEntry entry) {
        return new Product(
                new ProductId(entry.id),
                new Title(entry.title),
                new Description(entry.description),
                new Price(entry.price)
        );
    }

    @NoArgsConstructor
    @Setter
    private static class ProductEntry {

        public String id;
        public String title;
        public String description;
        public float price;
    }
}
