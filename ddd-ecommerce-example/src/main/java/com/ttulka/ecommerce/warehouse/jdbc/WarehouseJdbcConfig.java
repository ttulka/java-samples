package com.ttulka.ecommerce.warehouse.jdbc;

import com.ttulka.ecommerce.common.EventPublisher;
import com.ttulka.ecommerce.warehouse.Warehouse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class WarehouseJdbcConfig {

    @Bean
    WarehouseJdbc warehouse(JdbcTemplate jdbcTemplate) {
        return new WarehouseJdbc(jdbcTemplate);
    }

    @Bean
    FetchGoodsJdbc fetchProducts(Warehouse warehouse, JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new FetchGoodsJdbc(warehouse, jdbcTemplate, eventPublisher);
    }

    @Bean
    RemoveFetchedGoodsJdbc removeFetchedGoodsJdbc(JdbcTemplate jdbcTemplate) {
        return new RemoveFetchedGoodsJdbc(jdbcTemplate);
    }
}
