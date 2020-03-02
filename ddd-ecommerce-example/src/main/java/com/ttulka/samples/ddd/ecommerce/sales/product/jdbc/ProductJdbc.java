package com.ttulka.samples.ddd.ecommerce.sales.product.jdbc;

import com.ttulka.samples.ddd.ecommerce.sales.product.Code;
import com.ttulka.samples.ddd.ecommerce.sales.product.Description;
import com.ttulka.samples.ddd.ecommerce.sales.product.Price;
import com.ttulka.samples.ddd.ecommerce.sales.product.Product;
import com.ttulka.samples.ddd.ecommerce.sales.product.ProductId;
import com.ttulka.samples.ddd.ecommerce.sales.product.Title;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode(of = "id")
final class ProductJdbc implements Product {

    private final ProductId id;
    private final Code code;
    private final Title title;
    private final Description description;
    private Price price;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ProductId id() {
        return id;
    }

    @Override
    public Code code() {
        return code;
    }

    @Override
    public Title title() {
        return title;
    }

    @Override
    public Description description() {
        return description;
    }

    @Override
    public Price price() {
        return price;
    }

    @Override
    public void changePrice(Price price) {
        this.price = price;
        jdbcTemplate.update("UPDATE products SET price = ? WHERE id = ?",
                            price.value(), id.value());
    }
}
