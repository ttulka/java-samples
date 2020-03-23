package com.ttulka.samples.ddd.ecommerce.sales.order.jdbc;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class OrderJdbcConfig {

    @Bean
    OrdersJdbc ordersJdbc(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new OrdersJdbc(jdbcTemplate, eventPublisher);
    }

    @Bean
    PlaceOrderJdbc placeOrder(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new PlaceOrderJdbc(jdbcTemplate, eventPublisher);
    }
}
