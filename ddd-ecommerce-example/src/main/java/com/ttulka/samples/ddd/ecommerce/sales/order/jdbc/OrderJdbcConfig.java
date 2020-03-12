package com.ttulka.samples.ddd.ecommerce.sales.order.jdbc;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.sales.order.PlaceOrder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class OrderJdbcConfig {

    @Bean
    PlaceOrder placeOrder(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new PlaceOrderJdbc(jdbcTemplate, eventPublisher);
    }

    @Bean
    OrdersJdbc ordersJdbc(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new OrdersJdbc(jdbcTemplate, eventPublisher);
    }
}
