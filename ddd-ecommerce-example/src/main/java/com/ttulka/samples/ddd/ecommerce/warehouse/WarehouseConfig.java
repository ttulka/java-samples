package com.ttulka.samples.ddd.ecommerce.warehouse;

import java.time.Instant;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.sales.OrderPlaced;
import com.ttulka.samples.ddd.ecommerce.shipping.DeliveryDispatched;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Configuration
class WarehouseConfig {

    @Bean("warehouse-orderPlacedListener")
    OrderPlacedListener orderPlacedListener(EventPublisher eventPublisher) {
        return new OrderPlacedListener(eventPublisher);
    }

    @Bean("warehouse-deliveryDispatchedListener")
    DeliveryDispatchedListener deliveryDispatchedListener() {
        return new DeliveryDispatchedListener();
    }

    @RequiredArgsConstructor
    private static final class OrderPlacedListener {

        private final @NonNull EventPublisher eventPublisher;

        @EventListener
        @Async
        @Order(10)
        public void on(OrderPlaced event) {
            System.out.println("WAREHOUSE: OrderPlaced " + event);
            // TODO fetch goods and remove from inventory
            eventPublisher.raise(new GoodsFetched(Instant.now(), event.orderId));
        }
    }

    @RequiredArgsConstructor
    private static final class DeliveryDispatchedListener {

        @EventListener
        @Async
        public void on(DeliveryDispatched event) {
            System.out.println("WAREHOUSE: DeliveryDispatched " + event);
            // TODO remove fetched goods
        }
    }
}
