package com.ttulka.samples.ddd.ecommerce.billing;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.sales.order.OrderPlaced;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;

import lombok.RequiredArgsConstructor;

@Configuration
class BillingConfig {

    @Bean("billing-orderPlacedListener")
    OrderPlacedListener orderPlacedListener(EventPublisher eventPublisher) {
        return new OrderPlacedListener(eventPublisher);
    }

    @RequiredArgsConstructor
    private static final class OrderPlacedListener {

        private final EventPublisher eventPublisher;

        @EventListener
        @Order(20)
        public void on(OrderPlaced event) {
            // TODO do the payment...
            new Payment(event.orderId, eventPublisher).confirm();
        }
    }
}
