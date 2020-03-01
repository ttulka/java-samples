package com.ttulka.samples.ddd.ecommerce.sales.product;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class ProductsConfig {

    @Bean
    FindProducts products(JdbcTemplate jdbcTemplate) {
        return new ProductsJdbc(jdbcTemplate);
    }
}
