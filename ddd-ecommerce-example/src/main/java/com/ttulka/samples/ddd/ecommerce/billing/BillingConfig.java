package com.ttulka.samples.ddd.ecommerce.billing;

import com.ttulka.samples.ddd.ecommerce.billing.payment.Money;
import com.ttulka.samples.ddd.ecommerce.billing.payment.Payment;
import com.ttulka.samples.ddd.ecommerce.billing.payment.ReferenceId;
import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.sales.OrderPlaced;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

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
        @Async
        @Order(20)
        public void on(OrderPlaced event) {
            new Payment(
                    new ReferenceId(event.orderId),
                    new Money(event.orderItems.stream()
                                      .mapToDouble(item -> item.price * item.quantity)
                                      .sum()),
                    eventPublisher)
                    .confirm();
        }
    }
}
