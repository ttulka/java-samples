package com.ttulka.ecommerce.billing.payment.jdbc;

import com.ttulka.ecommerce.common.events.EventPublisher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Configuration for JDBC implementation for Payment domain.
 */
@Configuration
class PaymentsJdbcConfig {

    @Bean
    PaymentsJdbc payments(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new PaymentsJdbc(jdbcTemplate, eventPublisher);
    }
}
