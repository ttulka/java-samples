package com.ttulka.samples.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql(statements = "INSERT INTO products VALUES " +
                  "  ('001', 'A', 3.00)," +
                  "  ('002', 'B', 2.50)," +
                  "  ('003', 'C', 1.00);")
class FindProductsTest {

    @Autowired
    private FindProducts findProducts;

    @Test
    void products_cheaper_than_an_amount_of_money_are_found() {

    }
}
