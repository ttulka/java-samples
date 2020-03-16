package com.ttulka.samples.ddd.ecommerce;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@SpringBootApplication
public class ECommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }

    @Bean
    EventPublisher eventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return applicationEventPublisher::publishEvent;
    }

    @Bean
    LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
