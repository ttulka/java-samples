package com.ttulka.samples.ddd.ecommerce.shipping;

import com.ttulka.samples.ddd.ecommerce.sales.order.OrderPlaced;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
class ShippingConfig {

    @Bean
    ShipOrder shipOrder() {
        return new ShipOrder();
    }

    @EventListener
    public void on(OrderPlaced event) {
        shipOrder().order(event.getOrderInfo());
    }
}
