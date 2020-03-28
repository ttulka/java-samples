package com.ttulka.ecommerce.sales.order.jdbc;

import com.ttulka.ecommerce.common.EventPublisher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Configuration for JDBC implementation for Order domain.
 */
@Configuration
class OrderJdbcConfig {

    @Bean
    OrdersJdbc ordersJdbc(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new OrdersJdbc(jdbcTemplate, eventPublisher);
    }
}
