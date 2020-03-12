package com.ttulka.samples.ddd.ecommerce.warehouse;

import com.ttulka.samples.ddd.ecommerce.shipping.DeliveryDispatched;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import lombok.RequiredArgsConstructor;

@Configuration
class WarehouseConfig {

    @Bean("warehouse-deliveryDispatchedListener")
    DeliveryDispatchedListener orderPlacedListener() {
        return new DeliveryDispatchedListener();
    }

    @RequiredArgsConstructor
    private static final class DeliveryDispatchedListener {

        @EventListener
        @Async
        @Order(20)
        public void on(DeliveryDispatched event) {
            // TODO
        }
    }
}
