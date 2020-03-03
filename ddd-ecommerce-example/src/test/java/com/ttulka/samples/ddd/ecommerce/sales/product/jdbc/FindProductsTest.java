package com.ttulka.samples.ddd.ecommerce.sales.product.jdbc;

import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@ContextConfiguration(classes = ProductsJdbcConfig.class)
@Sql("/test-data-sales-find-products.sql")
@Transactional
class FindProductsTest {

    @Autowired
    private FindProducts findProducts;

    @Test
    void all_products_are_found() {
        List<ProductId> productIds = findProducts.all().stream()
                .map(Product::id)
                .collect(Collectors.toList());

        assertThat(productIds).containsExactlyInAnyOrder(
                new ProductId(1L), new ProductId(2L), new ProductId(3L));
    }

    @Test
    void products_from_a_category_are_found() {
        List<ProductId> productIds = findProducts.fromCategory("cat1").stream()
                .map(Product::id)
                .collect(Collectors.toList());

        assertThat(productIds).containsExactlyInAnyOrder(
                new ProductId(1L), new ProductId(2L));
    }

    @Test
    void product_by_id_is_found() {
        Product product = findProducts.byId("1");
        assertAll(
                () -> assertThat(product.id()).isEqualTo(new ProductId(1L)),
                () -> assertThat(product.code()).isEqualTo(new Code("11111111-1111-1111-1111-111111111111")),
                () -> assertThat(product.title()).isEqualTo(new Title("Prod 1")),
                () -> assertThat(product.description()).isEqualTo(new Description("Prod 1 Desc")),
                () -> assertThat(product.price()).isEqualTo(new Price(1.f))
        );
    }
}
