package com.ttulka.ecommerce.billing.listeners;

import com.ttulka.ecommerce.billing.CollectPayment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BillingListenersConfig {

    @Bean("billing-orderPlacedListener")
    OrderPlacedListener orderPlacedListener(CollectPayment collectPayment) {
        return new OrderPlacedListener(collectPayment);
    }
}
