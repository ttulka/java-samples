package com.ttulka.samples.ddd.ecommerce.warehouse.jdbc;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.warehouse.FetchGoods;
import com.ttulka.samples.ddd.ecommerce.warehouse.RemoveFetchedGoods;
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

    @Bean
    FetchGoods fetchProducts(Warehouse warehouse, JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new FetchGoodsJdbc(warehouse, jdbcTemplate, eventPublisher);
    }

    @Bean
    RemoveFetchedGoods removeFetchedGoodsJdbc(JdbcTemplate jdbcTemplate) {
        return new RemoveFetchedGoodsJdbc(jdbcTemplate);
    }
}
