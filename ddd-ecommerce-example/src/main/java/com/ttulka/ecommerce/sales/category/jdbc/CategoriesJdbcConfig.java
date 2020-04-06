package com.ttulka.ecommerce.sales.category.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Configuration for JDBC implementation for Category domain.
 */
@Configuration
class CategoriesJdbcConfig {

    @Bean
    FindCategoriesJdbc findCategories(JdbcTemplate jdbcTemplate) {
        return new FindCategoriesJdbc(jdbcTemplate);
    }
}