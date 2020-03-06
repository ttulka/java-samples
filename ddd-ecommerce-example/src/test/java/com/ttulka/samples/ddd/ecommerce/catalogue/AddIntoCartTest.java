package com.ttulka.samples.ddd.ecommerce.catalogue;

import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Amount;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.Cart;
import com.ttulka.samples.ddd.ecommerce.catalogue.cart.cookies.CartCookies;
import com.ttulka.samples.ddd.ecommerce.sales.product.FindProducts;
import com.ttulka.samples.ddd.ecommerce.warehouse.Warehouse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@ContextConfiguration(classes = {CatalogueConfig.class, AddIntoCartTest.ServicesTestConfig.class})
@Sql(statements = "INSERT INTO products VALUES (1, 'test-1', 'Test 1', 'Test', 1.00), (2, 'test-2', 'Test 2', 'Test', 2.00);")
@Transactional
class AddIntoCartTest {

    @Autowired
    FindProducts findProducts;

    @Test
    void item_is_added() {
        Cart cart = new CartCookies(new MockHttpServletRequest(), new MockHttpServletResponse());
        AddIntoCart addIntoCart = new AddIntoCart(cart, findProducts);

        addIntoCart.product("test-1", 123);
        assertAll(
                () -> assertThat(cart.items()).hasSize(1),
                () -> assertThat(cart.items().get(0).productCode()).isEqualTo("test-1"),
                () -> assertThat(cart.items().get(0).title()).isEqualTo("Test 1"),
                () -> assertThat(cart.items().get(0).amount()).isEqualTo(new Amount(123))
        );
    }

    @Test
    void multiple_items_are_added() {
        Cart cart = new CartCookies(new MockHttpServletRequest(), new MockHttpServletResponse());
        AddIntoCart addIntoCart = new AddIntoCart(cart, findProducts);

        addIntoCart.product("test-1", 123);
        addIntoCart.product("test-2", 321);
        assertAll(
                () -> assertThat(cart.items()).hasSize(2),
                () -> assertThat(cart.items().get(0).productCode()).isEqualTo("test-1"),
                () -> assertThat(cart.items().get(0).title()).isEqualTo("Test 1"),
                () -> assertThat(cart.items().get(0).amount()).isEqualTo(new Amount(123)),
                () -> assertThat(cart.items().get(1).productCode()).isEqualTo("test-2"),
                () -> assertThat(cart.items().get(1).title()).isEqualTo("Test 2"),
                () -> assertThat(cart.items().get(1).amount()).isEqualTo(new Amount(321))
        );
    }

    @Configuration
    @ComponentScan("com.ttulka.samples.ddd.ecommerce.sales")
    static class ServicesTestConfig {
        @MockBean
        private Warehouse warehouse;
    }
}
