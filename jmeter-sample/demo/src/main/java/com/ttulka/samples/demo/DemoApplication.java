package com.ttulka.samples.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RestController
    static class DemoController {

        @GetMapping
        Object get() {
            return new DemoData("demo");
        }

        @PostMapping
        String post(@RequestBody DemoData data) {
            return data.getData();
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        static class DemoData {
            String data;
        }
    }
}
