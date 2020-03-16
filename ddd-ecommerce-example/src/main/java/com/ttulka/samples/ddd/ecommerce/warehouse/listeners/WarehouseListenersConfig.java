package com.ttulka.samples.ddd.ecommerce.warehouse.listeners;

import com.ttulka.samples.ddd.ecommerce.warehouse.FetchGoods;
import com.ttulka.samples.ddd.ecommerce.warehouse.RemoveFetchedGoods;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class WarehouseListenersConfig {

    @Bean("warehouse-orderPlacedListener")
    OrderPlacedListener orderPlacedListener(FetchGoods fetchGoods) {
        return new OrderPlacedListener(fetchGoods);
    }

    @Bean("warehouse-deliveryDispatchedListener")
    DeliveryDispatchedListener deliveryDispatchedListener(RemoveFetchedGoods removeFetchedGoods) {
        return new DeliveryDispatchedListener(removeFetchedGoods);
    }

}
