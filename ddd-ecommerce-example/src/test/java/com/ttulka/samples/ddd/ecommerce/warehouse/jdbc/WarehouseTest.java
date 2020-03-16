package com.ttulka.samples.ddd.ecommerce.warehouse.jdbc;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.warehouse.Amount;
import com.ttulka.samples.ddd.ecommerce.warehouse.InStock;
import com.ttulka.samples.ddd.ecommerce.warehouse.ProductCode;
import com.ttulka.samples.ddd.ecommerce.warehouse.Warehouse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = WarehouseJdbcConfig.class)
@Sql(statements = "INSERT INTO products_in_stock VALUES ('test-1', 123), ('test-2', 321);")
class WarehouseTest {

    @Autowired
    private Warehouse warehouse;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void left_in_stock_returned() {
        InStock inStock = warehouse.leftInStock(new ProductCode("test-1"));
        assertThat(inStock).isEqualTo(new InStock(123));
    }

    @Test
    void zero_left_in_stock_returned_for_an_unknown_product() {
        InStock inStock = warehouse.leftInStock(new ProductCode("XXX"));
        assertThat(inStock).isEqualTo(new InStock(0));
    }

    @Test
    void product_is_put_into_stock() {
        warehouse.putIntoStock(new ProductCode("test-xxx"), new Amount(123));
        assertThat(warehouse.leftInStock(new ProductCode("test-xxx"))).isEqualTo(new InStock(123));
    }
}
