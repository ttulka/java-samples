package com.ttulka.samples.ddd.ecommerce.shipping;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class DeliveryItemTest {

    @Test
    void delivery_item_values() {
        DeliveryItem deliveryItem = new DeliveryItem(
                new ProductCode("test"), new Amount(123));

        assertAll(
                () -> assertThat(deliveryItem.productCode()).isEqualTo(new ProductCode("test")),
                () -> assertThat(deliveryItem.amount()).isEqualTo(new Amount(123))
        );
    }
}
