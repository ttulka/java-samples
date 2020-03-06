package com.ttulka.samples.ddd.ecommerce;

import javax.sql.DataSource;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

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

    @Configuration
    @ConditionalOnClass(name = "org.h2.Driver")
    static class ExampleDatabaseConfig {
        @Bean
        DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .addScripts("/schema.sql", "/example-data.sql")
                    .build();
        }
    }
}
