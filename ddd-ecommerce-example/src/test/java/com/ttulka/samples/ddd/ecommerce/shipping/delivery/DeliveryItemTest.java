package com.ttulka.samples.ddd.ecommerce.shipping.delivery;

import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryItem;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.ProductCode;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Quantity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class DeliveryItemTest {

    @Test
    void delivery_item_values() {
        DeliveryItem deliveryItem = new DeliveryItem(
                new ProductCode("test"), new Quantity(123));

        assertAll(
                () -> assertThat(deliveryItem.productCode()).isEqualTo(new ProductCode("test")),
                () -> assertThat(deliveryItem.quantity()).isEqualTo(new Quantity(123))
        );
    }
}