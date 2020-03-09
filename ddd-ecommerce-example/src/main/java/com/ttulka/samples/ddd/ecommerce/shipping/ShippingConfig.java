package com.ttulka.samples.ddd.ecommerce.shipping;

import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.billing.OrderPaid;
import com.ttulka.samples.ddd.ecommerce.sales.order.OrderPlaced;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
class ShippingConfig {

    // TODO
    @Bean
    Deliveries deliveries() {
        return new Deliveries();
    }

    @EventListener
    public void on(OrderPlaced event) {
        new Delivery(event.orderItems.stream()
                             .map(item -> new Delivery.Item(
                                     item.code,
                                     item.amount))
                             .collect(Collectors.toList()),
                     new Delivery.Address(
                             event.customer.name,
                             event.customer.address))
                .prepare();
    }

    @EventListener
    public void on(OrderPaid event) {
        deliveries().byOrderId(event.orderId)
                .ship();
    }
}
