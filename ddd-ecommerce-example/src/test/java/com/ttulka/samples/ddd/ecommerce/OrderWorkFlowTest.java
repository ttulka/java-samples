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

import static io.restassured.RestAssured.with;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql("/test-data-order-workflow.sql")
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
                .param("productCode", "OW001")
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

        Object orderId = with()
                .port(port)
                .basePath("/delivery")
                .get()
                .andReturn()
                .jsonPath().getMap("[0]").get("orderId");

        assertThat(orderId).isNotNull().as("No delivery found for a new order.");

        JsonPath deliveryJson = with()
                .port(port)
                .basePath("/delivery")
                .get("/order/" + orderId)
                .andReturn()
                .jsonPath();

        assertAll(
                () -> assertThat(deliveryJson.getBoolean("dispatched")).isTrue().as("Delivery is not dispatched."),
                () -> assertThat(deliveryJson.getMap("address").get("person")).isEqualTo("test name"),
                () -> assertThat(deliveryJson.getMap("address").get("place")).isEqualTo("test address"),
                () -> assertThat(deliveryJson.getList("items")).hasSize(1),
                () -> assertThat(deliveryJson.<Map>getList("items").get(0).get("code")).isEqualTo("OW001"),
                () -> assertThat(deliveryJson.<Map>getList("items").get(0).get("quantity")).isEqualTo(1));
    }

    @Test
    void delivered_items_are_removed_from_stock() {
        CookieFilter cookieFilter = new CookieFilter(); // share cookies among requests

        with() // add an cart item
                .filter(cookieFilter)
                .port(port)
                .basePath("/cart")
                .param("productCode", "OW002")
                .param("quantity", 123)
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

        // (1000-123) left in stock
        String leftInStock = with()
                .port(port)
                .basePath("/warehouse/stock")
                .get("OW002")
                .andReturn()
                .body().print();

        assertThat(leftInStock).isEqualTo(String.valueOf(1000 - 123)).as("Items are not removed from the stock.");
    }

    @Test
    void payment_for_an_order_is_collected() {
        CookieFilter cookieFilter = new CookieFilter(); // share cookies among requests

        with() // add an cart item
                .filter(cookieFilter)
                .port(port)
                .basePath("/cart")
                .param("productCode", "OW003")
                .param("quantity", 3)
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

        // payment is collected

        Double totalPayment = with()
                .port(port)
                .basePath("/payment")
                .get()
                .andReturn()
                .jsonPath().getDouble("[0].total");

        assertThat(totalPayment).isEqualTo(10.5).as("Payment does not match.");
    }
}