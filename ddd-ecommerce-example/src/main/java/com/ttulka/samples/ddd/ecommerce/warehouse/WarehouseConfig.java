package com.ttulka.samples.ddd.ecommerce.warehouse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class WarehouseConfig {

    @Bean
    Warehouse warehouse() {
        return new WarehouseJdbc();
    }
}
