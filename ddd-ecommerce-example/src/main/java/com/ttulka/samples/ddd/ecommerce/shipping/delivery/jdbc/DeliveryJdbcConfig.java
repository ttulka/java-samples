package com.ttulka.samples.ddd.ecommerce.shipping.delivery.jdbc;

import com.ttulka.samples.ddd.ecommerce.shipping.FindDeliveries;
import com.ttulka.samples.ddd.ecommerce.shipping.PrepareDelivery;
import com.ttulka.samples.ddd.ecommerce.shipping.DispatchDelivery;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class DeliveryJdbcConfig {

    @Bean
    FindDeliveries deliveries(JdbcTemplate jdbcTemplate) {
        return new DeliveriesJdbc(jdbcTemplate);
    }

    @Bean
    PrepareDelivery prepareDelivery(JdbcTemplate jdbcTemplate) {
        return new PrepareDeliveryJdbc(jdbcTemplate);
    }

    @Bean
    DispatchDelivery dispatchDelivery(FindDeliveries findDeliveries) {
        return new DispatchDelivery(findDeliveries);
    }
}
