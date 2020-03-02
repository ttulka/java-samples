package com.ttulka.samples.ddd.ecommerce.sales.product.jdbc;

import java.util.List;
import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.sales.product.FindProducts;
import com.ttulka.samples.ddd.ecommerce.sales.product.Product;
import com.ttulka.samples.ddd.ecommerce.sales.product.ProductId;

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
@Sql("/test-data-sales-find-products.sql")
class FindProductsTest {

    @Autowired
    private FindProducts findProducts;

    @Test
    void all_products_are_found() {
        List<String> productIds = findProducts.all().stream()
                .map(Product::id)
                .map(ProductId::value)
                .collect(Collectors.toList());

        assertThat(productIds).containsExactlyInAnyOrder("1", "2", "3");
    }

    @Test
    void products_from_a_category_are_found() {
        List<String> productIds = findProducts.fromCategory("cat1").stream()
                .map(Product::id)
                .map(ProductId::value)
                .collect(Collectors.toList());

        assertThat(productIds).containsExactlyInAnyOrder("1", "2");
    }

    @Test
    void product_by_id_is_found() {
        Product product = findProducts.byId("1");

        assertThat(product.id().value()).isEqualTo("1");
        assertThat(product.code().value()).isEqualTo("6ef8c68e-62cf-4697-a4f9-1950181097bb");
        assertThat(product.title().value()).isEqualTo("Prod 1");
        assertThat(product.description().value()).isEqualTo("Prod 1 Desc");
        assertThat(product.price().value()).isEqualTo(1.f);
    }
}
