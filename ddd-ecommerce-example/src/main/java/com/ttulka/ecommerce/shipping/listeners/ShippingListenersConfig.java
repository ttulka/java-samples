package com.ttulka.ecommerce.shipping.listeners;

import com.ttulka.ecommerce.shipping.PrepareDelivery;
import com.ttulka.ecommerce.shipping.UpdateDelivery;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ShippingListenersConfig {

    @Bean("shipping-orderPlacedListener")
    OrderPlacedListener orderPlacedListener(PrepareDelivery prepareDelivery) {
        return new OrderPlacedListener(prepareDelivery);
    }

    @Bean("shipping-dispatchingListener")
    DispatchingListener dispatchingListener(UpdateDelivery updateDelivery) {
        return new DispatchingListener(updateDelivery);
    }

}
