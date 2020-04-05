package com.ttulka.ecommerce.sales.product.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Configuration for JDBC implementation for Product domain.
 */
@Configuration
class ProductsJdbcConfig {

    @Bean
    FindProductsJdbc findProductsJdbc(JdbcTemplate jdbcTemplate) {
        return new FindProductsJdbc(jdbcTemplate);
    }
}
