package com.ttulka.samples.ddd.ecommerce.sales.product;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class ProductJdbc implements Product {

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
                            new Object[]{price.value(), id.value()});
    }
}
