package com.ttulka.samples.ddd.ecommerce.billing.payment.jdbc;

import com.ttulka.samples.ddd.ecommerce.billing.CollectPayment;
import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
class PaymentJdbcConfig {

    @Bean
    PaymentsJdbc payments(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new PaymentsJdbc(jdbcTemplate, eventPublisher);
    }

    @Bean
    CollectPayment collectPayment(JdbcTemplate jdbcTemplate, EventPublisher eventPublisher) {
        return new CollectPaymentJdbc(jdbcTemplate, eventPublisher);
    }
}
