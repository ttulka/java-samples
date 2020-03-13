package com.ttulka.samples.ddd.ecommerce.shipping.delivery.jdbc;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.shipping.FindDeliveries;
import com.ttulka.samples.ddd.ecommerce.shipping.PrepareDelivery;
import com.ttulka.samples.ddd.ecommerce.shipping.UpdateDelivery;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class DeliveryJdbcConfig {

    @Bean
    FindDeliveries deliveries(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new DeliveriesJdbc(jdbcTemplate, eventPublisher);
    }

    @Bean
    PrepareDelivery prepareDelivery(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new PrepareDeliveryJdbc(jdbcTemplate, eventPublisher);
    }

    @Bean
    UpdateDelivery updateDelivery(FindDeliveries findDeliveries) {
        return new UpdateDelivery(findDeliveries);
    }
}
