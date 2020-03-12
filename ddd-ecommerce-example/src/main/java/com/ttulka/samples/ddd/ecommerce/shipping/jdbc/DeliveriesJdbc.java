package com.ttulka.samples.ddd.ecommerce.shipping.jdbc;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.shipping.Address;
import com.ttulka.samples.ddd.ecommerce.shipping.Quantity;
import com.ttulka.samples.ddd.ecommerce.shipping.Deliveries;
import com.ttulka.samples.ddd.ecommerce.shipping.Delivery;
import com.ttulka.samples.ddd.ecommerce.shipping.DeliveryId;
import com.ttulka.samples.ddd.ecommerce.shipping.DeliveryItem;
import com.ttulka.samples.ddd.ecommerce.shipping.OrderId;
import com.ttulka.samples.ddd.ecommerce.shipping.Person;
import com.ttulka.samples.ddd.ecommerce.shipping.Place;
import com.ttulka.samples.ddd.ecommerce.shipping.ProductCode;
import com.ttulka.samples.ddd.ecommerce.shipping.UnknownDelivery;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
final class DeliveriesJdbc implements Deliveries {

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public Delivery byOrderId(OrderId orderId) {
        try {
            Map<String, Object> delivery = jdbcTemplate.queryForMap(
                    "SELECT id, order_id orderId, person, place, dispatched FROM deliveries " +
                    "WHERE order_id = ?", orderId.value());

            List<Map<String, Object>> items = jdbcTemplate.queryForList(
                    "SELECT product_code productCode, quantity FROM delivery_items " +
                    "WHERE delivery_id = ?", delivery.get("id"));

            if (delivery != null && items != null) {
                return toDelivery(delivery, items);
            }
        } catch (DataAccessException ignore) {
            log.warn("Delivery by order id {} was not found: {}", orderId, ignore.getMessage());
        }
        return new UnknownDelivery();
    }

    private DeliveryJdbc toDelivery(Map<String, Object> delivery, List<Map<String, Object>> items) {
        return new DeliveryJdbc(
                new DeliveryId(delivery.get("id")),
                new OrderId(delivery.get("orderId")),
                items.stream()
                        .map(item -> new DeliveryItem(
                                new ProductCode((String) item.get("productCode")),
                                new Quantity((Integer) item.get("quantity"))))
                        .collect(Collectors.toList()),
                new Address(
                        new Person((String) delivery.get("person")),
                        new Place((String) delivery.get("place"))),
                jdbcTemplate,
                true,
                (Boolean) delivery.get("dispatched"));
    }
}
