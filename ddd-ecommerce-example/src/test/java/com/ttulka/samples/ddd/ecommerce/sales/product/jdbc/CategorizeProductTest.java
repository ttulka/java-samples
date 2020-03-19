package com.ttulka.samples.ddd.ecommerce.sales.product.jdbc;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.sales.FindProducts;
import com.ttulka.samples.ddd.ecommerce.sales.category.CategoryId;
import com.ttulka.samples.ddd.ecommerce.sales.category.Uri;
import com.ttulka.samples.ddd.ecommerce.sales.product.Code;
import com.ttulka.samples.ddd.ecommerce.sales.product.Description;
import com.ttulka.samples.ddd.ecommerce.sales.product.Price;
import com.ttulka.samples.ddd.ecommerce.sales.product.Product;
import com.ttulka.samples.ddd.ecommerce.sales.product.ProductId;
import com.ttulka.samples.ddd.ecommerce.sales.product.Title;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@ContextConfiguration(classes = ProductsJdbcConfig.class)
@Sql(statements = "INSERT INTO categories VALUES ('123', 'test-category', 'Test Category')")
class CategorizeProductTest {

    @Autowired
    private FindProducts findProducts;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void product_is_categorized() {
        Product product = new ProductJdbc(
                new ProductId(456),
                new Code("test"),
                new Title("test"),
                new Description("test"),
                new Price(1.f),
                jdbcTemplate
        );
        product.putForSale();

        product.categorize(new CategoryId(123));

        List<Product> productsFound = findProducts.fromCategory(new Uri("test-category"));
        assertAll(
                () -> assertThat(productsFound).hasSize(1),
                () -> assertThat(productsFound.get(0).id()).isEqualTo(new ProductId(456))
        );
    }
}
