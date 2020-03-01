package com.ttulka.samples.ddd.ecommerce.sales.category.jdbc;

import com.ttulka.samples.ddd.ecommerce.sales.category.Categories;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
final class CategoriesJdbcConfig {

    @Bean
    Categories categories(JdbcTemplate jdbcTemplate) {
        return new CategoriesJdbc(jdbcTemplate);
    }
}
