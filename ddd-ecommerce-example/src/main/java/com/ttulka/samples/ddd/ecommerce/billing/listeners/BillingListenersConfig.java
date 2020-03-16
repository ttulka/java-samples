package com.ttulka.samples.ddd.ecommerce.billing.listeners;

import com.ttulka.samples.ddd.ecommerce.billing.CollectPayment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BillingListenersConfig {

    @Bean("billing-orderPlacedListener")
    OrderPlacedListener orderPlacedListener(CollectPayment collectPayment) {
        return new OrderPlacedListener(collectPayment);
    }
}
