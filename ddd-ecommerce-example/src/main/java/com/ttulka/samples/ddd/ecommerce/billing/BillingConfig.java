package com.ttulka.samples.ddd.ecommerce.billing;

import com.ttulka.samples.ddd.ecommerce.billing.payment.Money;
import com.ttulka.samples.ddd.ecommerce.billing.payment.ReferenceId;
import com.ttulka.samples.ddd.ecommerce.sales.OrderPlaced;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Configuration
class BillingConfig {

    @Bean("billing-orderPlacedListener")
    OrderPlacedListener orderPlacedListener(CollectPayment collectPayment) {
        return new OrderPlacedListener(collectPayment);
    }

    @RequiredArgsConstructor
    private static final class OrderPlacedListener {

        private final @NonNull CollectPayment collectPayment;

        @TransactionalEventListener
        @Async
        public void on(OrderPlaced event) {
            collectPayment.collect(
                    new ReferenceId(event.orderId),
                    new Money(event.orderItems.stream()
                                      .mapToDouble(item -> item.price * item.quantity)
                                      .sum()));
        }
    }
}
