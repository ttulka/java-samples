package com.ttulka.samples.ddd.ecommerce.shipping.delivery.jdbc;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.shipping.DispatchDelivery;
import com.ttulka.samples.ddd.ecommerce.shipping.FindDeliveries;
import com.ttulka.samples.ddd.ecommerce.shipping.PrepareDelivery;

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
    DispatchDelivery dispatchDelivery(FindDeliveries findDeliveries) {
        return new DispatchDelivery(findDeliveries);
    }
}
