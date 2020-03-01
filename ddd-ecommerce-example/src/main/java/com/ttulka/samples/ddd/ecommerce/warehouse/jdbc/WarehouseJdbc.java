package com.ttulka.samples.ddd.ecommerce.warehouse.jdbc;

import com.ttulka.samples.ddd.ecommerce.warehouse.Warehouse;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
final class WarehouseJdbc implements Warehouse {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public int leftInStock(String productId) {
        Integer leftInStock = jdbcTemplate.queryForObject(
                "SELECT amount FROM products_in_stock " +
                "WHERE product_id = ?",
                new Object[]{productId},
                Integer.class);
        return leftInStock != null ? leftInStock : 0;
    }
}
