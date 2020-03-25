package com.ttulka.ecommerce.shipping.delivery.jdbc;

import com.ttulka.ecommerce.common.EventPublisher;
import com.ttulka.ecommerce.shipping.FindDeliveries;
import com.ttulka.ecommerce.shipping.UpdateDelivery;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class DeliveryJdbcConfig {

    @Bean
    DeliveriesJdbc deliveries(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new DeliveriesJdbc(jdbcTemplate, eventPublisher);
    }

    @Bean
    UpdateDelivery updateDelivery(FindDeliveries findDeliveries) {
        return new UpdateDelivery(findDeliveries);
    }
}
