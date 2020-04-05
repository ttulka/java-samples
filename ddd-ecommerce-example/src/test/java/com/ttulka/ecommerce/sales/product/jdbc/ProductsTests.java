package com.ttulka.ecommerce.sales.product.jdbc;

import java.util.List;
import java.util.stream.Collectors;

import com.ttulka.ecommerce.sales.product.Code;
import com.ttulka.ecommerce.sales.product.Product;
import com.ttulka.ecommerce.sales.product.Products;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@JdbcTest
@ContextConfiguration(classes = ProductsJdbcConfig.class)
@Sql(statements = "INSERT INTO products VALUES " +
        "('000', 'code0', 'F', 'desc0', 9.0), " +
        "('001', 'code1', 'G', 'desc1', 8.0), " +
        "('002', 'code2', 'H', 'desc2', 7.0), " +
        "('003', 'code3', 'I', 'desc3', 6.0), " +
        "('004', 'code4', 'J', 'desc4', 5.0), " +
        "('005', 'code5', 'A', 'desc5', 4.0), " +
        "('006', 'code6', 'B', 'desc6', 3.0), " +
        "('007', 'code7', 'C', 'desc7', 2.0), " +
        "('008', 'code8', 'D', 'desc8', 1.0), " +
        "('009', 'code9', 'E', 'desc9', 0.0)")
class ProductsTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void products_are_iterated() {
        String query = "SELECT id, code, title, description, price FROM products " +
                "WHERE id IN (?, ?)";
        Products products = new ProductsJdbc(query, List.of("000", "001"), jdbcTemplate);

        List<Product> list = products.stream().collect(Collectors.toList());
        assertAll(
                () -> assertThat(list.size()).isEqualTo(2),
                () -> assertThat(list.get(0).code()).isEqualTo(new Code("code0")),
                () -> assertThat(list.get(1).code()).isEqualTo(new Code("code1"))
        );
    }

    @Test
    void products_are_sorted_by_title() {
        String query = "SELECT id, code, title, description, price FROM products " +
                "WHERE id IN (?, ?)";
        Products products = new ProductsJdbc(query, List.of("004", "005"), jdbcTemplate)
                .sorted(Products.SortBy.TITLE);

        List<Product> list = products.stream().collect(Collectors.toList());
        assertAll(
                () -> assertThat(list.size()).isEqualTo(2),
                () -> assertThat(list.get(0).code()).isEqualTo(new Code("code5")),
                () -> assertThat(list.get(1).code()).isEqualTo(new Code("code4"))
        );
    }

    @Test
    void products_are_sorted_by_price() {
        String query = "SELECT id, code, title, description, price FROM products " +
                "WHERE id IN (?, ?)";
        Products products = new ProductsJdbc(query, List.of("007", "008"), jdbcTemplate)
                .sorted(Products.SortBy.PRICE);

        List<Product> list = products.stream().collect(Collectors.toList());
        assertAll(
                () -> assertThat(list.size()).isEqualTo(2),
                () -> assertThat(list.get(0).code()).isEqualTo(new Code("code8")),
                () -> assertThat(list.get(1).code()).isEqualTo(new Code("code7"))
        );
    }

    @Test
    void products_are_limited() {
        String query = "SELECT id, code, title, description, price FROM products";
        Products products = new ProductsJdbc(query, List.of(), jdbcTemplate)
                .limited(3, 2);

        List<Product> list = products.stream().collect(Collectors.toList());
        assertAll(
                () -> assertThat(list.size()).isEqualTo(2),
                () -> assertThat(list.get(0).code()).isEqualTo(new Code("code3")),
                () -> assertThat(list.get(1).code()).isEqualTo(new Code("code4"))
        );
    }

    @Test
    void limited_start_is_greater_than_zero() {
        String query = "SELECT id, code, title, description, price FROM products";
        Products products = new ProductsJdbc(query, List.of(), jdbcTemplate);
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> products.limited(-1, 1)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> products.limited(1).limited(-1, 1))
        );
    }

    @Test
    void limited_limit_is_greater_than_zero() {
        String query = "SELECT id, code, title, description, price FROM products";
        Products products = new ProductsJdbc(query, List.of(), jdbcTemplate);
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> products.limited(0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> products.limited(-1)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> products.limited(1).limited(0)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> products.limited(1).limited(-1))
        );
    }

    @Test
    void products_are_sorted_and_limited() {
        String query = "SELECT id, code, title, description, price FROM products";
        Products products = new ProductsJdbc(query, List.of(), jdbcTemplate)
                .sorted(Products.SortBy.TITLE).limited(4)
                .sorted(Products.SortBy.PRICE).limited(1, 2)
                .sorted(Products.SortBy.DEFAULT).limited(1);

        List<Product> list = products.stream().collect(Collectors.toList());
        assertAll(
                () -> assertThat(list.size()).isEqualTo(1),
                () -> assertThat(list.get(0).code()).isEqualTo(new Code("code6"))
        );
    }

    @Test
    void products_are_fetched_only_once() {
        JdbcTemplate jdbcTemplateSpied = spy(jdbcTemplate);
        String query = "SELECT id, code, title, description, price FROM products";
        new ProductsJdbc(query, List.of(), jdbcTemplateSpied)
                .sorted(Products.SortBy.TITLE).limited(4)
                .sorted(Products.SortBy.PRICE).limited(1, 2)
                .sorted(Products.SortBy.DEFAULT).limited(1)
                .stream().close();

        verify(jdbcTemplateSpied, times(1)).queryForList(anyString(), anyInt(), anyInt());
    }
}
