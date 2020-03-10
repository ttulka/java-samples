package com.ttulka.samples.ddd.ecommerce.shipping.jdbc;

import com.ttulka.samples.ddd.ecommerce.shipping.Deliveries;
import com.ttulka.samples.ddd.ecommerce.shipping.PrepareDelivery;
import com.ttulka.samples.ddd.ecommerce.shipping.ShipDelivery;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class DeliveryJdbcConfig {

    @Bean
    Deliveries deliveries(JdbcTemplate jdbcTemplate) {
        return new DeliveriesJdbc(jdbcTemplate);
    }

    @Bean
    PrepareDelivery prepareDelivery(JdbcTemplate jdbcTemplate) {
        return new PrepareDeliveryJdbc(jdbcTemplate);
    }

    @Bean
    ShipDelivery shipDelivery(Deliveries deliveries) {
        return new ShipDelivery(deliveries);
    }
}
