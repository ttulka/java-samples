package com.ttulka.samples.ddd.ecommerce.sales.product.jdbc;

import com.ttulka.samples.ddd.ecommerce.sales.product.FindProducts;
import com.ttulka.samples.ddd.ecommerce.sales.product.Price;
import com.ttulka.samples.ddd.ecommerce.sales.product.Product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(ProductsJdbcConfig.class)
@Transactional
@Sql(statements = "INSERT INTO products VALUES (1, '6ef8c68e-62cf-4697-a4f9-1950181097bb', 'Test', 'Test', 1.00);")
class ChangeProductPriceTest {

    @Autowired
    private FindProducts findProducts;

    @Test
    void product_price_is_changed() {
        Product product = findProducts.byId("1");
        product.changePrice(new Price(100.5f));

        Product productUpdated = findProducts.byId("1");

        assertThat(productUpdated.price().value()).isEqualTo(100.5f);
    }
}
