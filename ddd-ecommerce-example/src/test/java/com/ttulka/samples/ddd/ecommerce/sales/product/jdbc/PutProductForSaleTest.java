package com.ttulka.samples.ddd.ecommerce.sales.product.jdbc;

import java.util.UUID;

import com.ttulka.samples.ddd.ecommerce.sales.product.Code;
import com.ttulka.samples.ddd.ecommerce.sales.product.Description;
import com.ttulka.samples.ddd.ecommerce.sales.product.FindProducts;
import com.ttulka.samples.ddd.ecommerce.sales.product.Price;
import com.ttulka.samples.ddd.ecommerce.sales.product.Product;
import com.ttulka.samples.ddd.ecommerce.sales.product.ProductId;
import com.ttulka.samples.ddd.ecommerce.sales.product.Title;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = ProductsJdbcConfig.class)
@Transactional
class PutProductForSaleTest {

    @Autowired
    private FindProducts findProducts;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void product_put_for_sale_is_found() {
        Product product = new ProductJdbc(
                new ProductId(123L),
                new Code(UUID.randomUUID().toString()),
                new Title("test"),
                new Description("test"),
                new Price(1.f),
                jdbcTemplate
        );
        product.putForSale();

        Product productFound = findProducts.byId("123");

        assertThat(productFound.id()).isEqualTo(new ProductId(123L));
    }
}