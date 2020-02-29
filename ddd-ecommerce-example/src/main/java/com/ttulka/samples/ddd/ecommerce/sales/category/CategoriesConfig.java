package com.ttulka.samples.ddd.ecommerce.sales.category;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class CategoriesConfig {

    @Bean
    Categories categories(JdbcTemplate jdbcTemplate) {
        return new CategoriesJdbc(jdbcTemplate);
    }
}
