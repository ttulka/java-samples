package com.ttulka.samples.ddd.ecommerce.sales.product.jdbc;

import com.ttulka.samples.ddd.ecommerce.sales.FindProducts;
import com.ttulka.samples.ddd.ecommerce.sales.product.Code;
import com.ttulka.samples.ddd.ecommerce.sales.product.Description;
import com.ttulka.samples.ddd.ecommerce.sales.product.Price;
import com.ttulka.samples.ddd.ecommerce.sales.product.Product;
import com.ttulka.samples.ddd.ecommerce.sales.product.Title;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = ProductsJdbcConfig.class)
@Sql(statements = "INSERT INTO products VALUES (1, 'test-1', 'Test', 'Test', 1.00);")
class ChangeProductTest {

    @Autowired
    private FindProducts findProducts;

    @Test
    void product_title_is_changed() {
        Product product = findProducts.byCode(new Code("test-1"));
        product.changeTitle(new Title("Updated title"));

        Product productUpdated = findProducts.byCode(new Code("test-1"));

        assertThat(productUpdated.title()).isEqualTo(new Title("Updated title"));
    }

    @Test
    void product_description_is_changed() {
        Product product = findProducts.byCode(new Code("test-1"));
        product.changeDescription(new Description("Updated description"));

        Product productUpdated = findProducts.byCode(new Code("test-1"));

        assertThat(productUpdated.description()).isEqualTo(new Description("Updated description"));
    }

    @Test
    void product_price_is_changed() {
        Product product = findProducts.byCode(new Code("test-1"));
        product.changePrice(new Price(100.5f));

        Product productUpdated = findProducts.byCode(new Code("test-1"));

        assertThat(productUpdated.price()).isEqualTo(new Price(100.5f));
    }
}
