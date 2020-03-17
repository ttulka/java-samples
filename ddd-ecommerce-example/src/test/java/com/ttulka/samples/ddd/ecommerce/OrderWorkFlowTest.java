package com.ttulka.samples.ddd.ecommerce;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import io.restassured.RestAssured;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.with;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test-data-order-workflow.sql")
@ActiveProfiles("test")
class OrderWorkFlowTest {

    @LocalServerPort
    private int port;

    @BeforeAll
    static void setupRestAssured() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void order_is_delivered() {
        CookieFilter cookieFilter = new CookieFilter(); // share cookies among requests

        with() // add an cart item
                .filter(cookieFilter)
                .port(port)
                .basePath("/cart")
                .param("productCode", "P001")
                .param("quantity", 1)
                .post()
                .andReturn();

        with() // place an order
                .filter(cookieFilter)
                .port(port)
                .basePath("/order")
                .formParam("name", "test name")
                .formParam("address", "test address")
                .post()
                .andReturn();

        // delivery is dispatched

        Object deliveryId = with()
                .port(port)
                .basePath("/delivery")
                .get()
                .andReturn()
                .jsonPath().getMap("[0]").get("id");

        assertThat(deliveryId).isNotNull().as("No delivery found for a new order.");

        JsonPath deliveryJson = with()
                .port(port)
                .basePath("/delivery")
                .get(deliveryId.toString())
                .andReturn()
                .jsonPath();

        assertAll(
                () -> assertThat(deliveryJson.getBoolean("dispatched")).isTrue().as("Delivery is not dispatched."),
                () -> assertThat(deliveryJson.getMap("address").get("person")).isEqualTo("test name"),
                () -> assertThat(deliveryJson.getMap("address").get("place")).isEqualTo("test address"),
                () -> assertThat(deliveryJson.getList("items")).hasSize(1),
                () -> assertThat(deliveryJson.<Map>getList("items").get(0).get("code")).isEqualTo("P001"),
                () -> assertThat(deliveryJson.<Map>getList("items").get(0).get("quantity")).isEqualTo(1));
    }
}