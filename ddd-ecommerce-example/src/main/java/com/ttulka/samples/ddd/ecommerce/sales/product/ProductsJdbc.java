package com.ttulka.samples.ddd.ecommerce.sales.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
final class ProductsJdbc implements FindProducts {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Product> all() {
        return jdbcTemplate.query("SELECT id, code, title, description, price FROM products",
                                  BeanPropertyRowMapper.newInstance(ProductEntry.class)).stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> fromCategory(String categoryId) {
        return jdbcTemplate.query("SELECT p.id, p.code, p.title, p.description, p.price FROM products AS p " +
                                  "JOIN products_in_categories AS pc ON pc.product_id = p.id " +
                                  "JOIN categories AS c ON c.id = pc.category_id " +
                                  "WHERE c.uri = ? ",
                                  new Object[]{categoryId},
                                  BeanPropertyRowMapper.newInstance(ProductEntry.class)).stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
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
