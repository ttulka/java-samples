package com.ttulka.samples.ddd.ecommerce.warehouse.jdbc;

import com.ttulka.samples.ddd.ecommerce.sales.product.ProductId;
import com.ttulka.samples.ddd.ecommerce.warehouse.InStock;
import com.ttulka.samples.ddd.ecommerce.warehouse.ProductCode;
import com.ttulka.samples.ddd.ecommerce.warehouse.Warehouse;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
final class WarehouseJdbc implements Warehouse {

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public InStock leftInStock(ProductCode productCode) {
        try {
            Integer leftInStock = jdbcTemplate.queryForObject(
                    "SELECT amount FROM products_in_stock " +
                    "WHERE product_code = ?",
                    new Object[]{productCode.value()},
                    Integer.class);
            if (leftInStock != null) {
                return new InStock(leftInStock);
            }
        } catch (DataAccessException ignore) {
        }
        return new InStock(0);
    }
}
