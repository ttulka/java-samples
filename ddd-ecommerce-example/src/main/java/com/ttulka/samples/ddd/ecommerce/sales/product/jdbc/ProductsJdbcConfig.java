package com.ttulka.samples.ddd.ecommerce.sales.product.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
final class ProductsJdbcConfig {

    @Bean
    ProductsJdbc products(JdbcTemplate jdbcTemplate) {
        return new ProductsJdbc(jdbcTemplate);
    }
}
