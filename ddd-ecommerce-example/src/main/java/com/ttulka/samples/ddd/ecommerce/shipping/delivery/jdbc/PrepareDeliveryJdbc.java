package com.ttulka.samples.ddd.ecommerce.shipping.delivery.jdbc;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Address;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryItem;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.PrepareDelivery;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
final class PrepareDeliveryJdbc implements PrepareDelivery {

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public void forOrder(OrderId orderId, List<DeliveryItem> items, Address address) {
        new DeliveryJdbc(new DeliveryId(orderId.value()), orderId, items, address, jdbcTemplate)
                .prepare();
    }
}
