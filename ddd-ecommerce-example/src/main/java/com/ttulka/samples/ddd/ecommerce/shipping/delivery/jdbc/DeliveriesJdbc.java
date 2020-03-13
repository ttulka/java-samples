package com.ttulka.samples.ddd.ecommerce.shipping.delivery.jdbc;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.shipping.FindDeliveries;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Address;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Delivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryItem;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Person;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Place;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.ProductCode;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Quantity;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.UnknownDelivery;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
final class DeliveriesJdbc implements FindDeliveries {

    private final @NonNull JdbcTemplate jdbcTemplate;
    private final @NonNull EventPublisher eventPublisher;

    @Override
    public Delivery byOrderId(OrderId orderId) {
        try {
            Map<String, Object> delivery = jdbcTemplate.queryForMap(
                    "SELECT id, order_id orderId, person, place, status FROM deliveries " +
                    "WHERE order_id = ?", orderId.value());

            List<Map<String, Object>> items = jdbcTemplate.queryForList(
                    "SELECT product_code productCode, quantity FROM delivery_items " +
                    "WHERE delivery_id = ?", delivery.get("id"));

            if (delivery != null && items != null) {
                return toDelivery(delivery, items);
            }
        } catch (DataAccessException ignore) {
            log.warn("Delivery by order ID {} was not found: {}", orderId, ignore.getMessage());
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
                Enum.valueOf(Delivery.Status.class, (String) delivery.get("status")),
                jdbcTemplate, eventPublisher);
    }
}
