package com.ttulka.samples.ddd.ecommerce.warehouse.jdbc;

import com.ttulka.samples.ddd.ecommerce.warehouse.Warehouse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class WarehouseJdbcConfig {

    @Bean
    Warehouse warehouse(JdbcTemplate jdbcTemplate) {
        return new WarehouseJdbc(jdbcTemplate);
    }
}
