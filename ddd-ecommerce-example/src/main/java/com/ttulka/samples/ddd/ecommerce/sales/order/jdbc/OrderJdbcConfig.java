package com.ttulka.samples.ddd.ecommerce.sales.order.jdbc;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.sales.order.PlaceOrder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OrderJdbcConfig {

    @Bean
    PlaceOrder placeOrder(EventPublisher eventPublisher) {
        return new PlaceOrderJdbc(eventPublisher);
    }
}
