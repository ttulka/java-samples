package com.ttulka.samples.ddd.ecommerce.shipping;

import com.ttulka.samples.ddd.ecommerce.sales.order.OrderPlaced;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
class ShippingConfig {

    @Bean
    ShipPackage shipOrder() {
        return new ShipPackage();
    }

    @EventListener
    public void on(OrderPlaced event) {
        // TODO
        shipOrder().ship(event.getOrder().toString());
    }
}
