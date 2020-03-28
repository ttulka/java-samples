package com.ttulka.ecommerce.shipping.delivery.jdbc;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ttulka.ecommerce.common.EventPublisher;
import com.ttulka.ecommerce.shipping.FindDeliveries;
import com.ttulka.ecommerce.shipping.PrepareDelivery;
import com.ttulka.ecommerce.shipping.delivery.Address;
import com.ttulka.ecommerce.shipping.delivery.Delivery;
import com.ttulka.ecommerce.shipping.delivery.DeliveryId;
import com.ttulka.ecommerce.shipping.delivery.DeliveryInfo;
import com.ttulka.ecommerce.shipping.delivery.DeliveryItem;
import com.ttulka.ecommerce.shipping.delivery.OrderId;
import com.ttulka.ecommerce.shipping.delivery.Person;
import com.ttulka.ecommerce.shipping.delivery.Place;
import com.ttulka.ecommerce.shipping.delivery.ProductCode;
import com.ttulka.ecommerce.shipping.delivery.Quantity;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for Delivery use-cases.
 */
@RequiredArgsConstructor
@Slf4j
class DeliveriesJdbc implements FindDeliveries, PrepareDelivery {

    private final @NonNull JdbcTemplate jdbcTemplate;
    private final @NonNull EventPublisher eventPublisher;

    @Override
    public List<DeliveryInfo> all() {
        List<Map<String, Object>> deliveries = jdbcTemplate.queryForList(
                "SELECT id, order_id orderId FROM deliveries");
        return deliveries.stream()
                .map(delivery -> new DeliveryInfo(
                        new DeliveryId(delivery.get("id")),
                        new OrderId(delivery.get("orderId"))))
                .collect(Collectors.toList());
    }

    @Transactional
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
                return toDelivery(delivery, items, isFetched(orderId), isPaid(orderId));
            }
        } catch (DataAccessException ignore) {
            log.debug("Delivery by order ID {} was not found.", orderId);
        }
        return new UnknownDeliveryJdbc(orderId, jdbcTemplate);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void prepare(@NonNull OrderId orderId, @NonNull List<DeliveryItem> items, @NonNull Address address) {
        Delivery delivery = new DeliveryJdbc(orderId, items, address, jdbcTemplate, eventPublisher);
        delivery.prepare();

        if (isFetched(orderId)) {
            delivery.markAsFetched();
        }
        if (isPaid(orderId)) {
            delivery.markAsPaid();
        }
    }

    private boolean isFetched(OrderId orderId) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(order_id) FROM delivery_fetched " +
                "WHERE order_id = ?", Integer.class, orderId.value()) > 0;
    }

    private boolean isPaid(OrderId orderId) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(order_id) FROM delivery_paid " +
                "WHERE order_id = ?", Integer.class, orderId.value()) > 0;
    }

    private DeliveryJdbc toDelivery(
            Map<String, Object> delivery, List<Map<String, Object>> items,
            boolean fetched, boolean paid) {
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
                mergedStatus(
                        Enum.valueOf(Delivery.Status.class, (String) delivery.get("status")),
                        fetched, paid),
                jdbcTemplate, eventPublisher);
    }

    private Delivery.Status mergedStatus(Delivery.Status status, boolean fetched, boolean paid) {
        if (fetched || paid) {
            switch (status) {
                case NEW:
                case PREPARED:
                    if (fetched) {
                        return Delivery.Status.FETCHED;
                    }
                    if (paid) {
                        return Delivery.Status.PAID;
                    }
                case FETCHED:
                    if (paid) {
                        return Delivery.Status.READY;
                    }
                    break;
                case PAID:
                    if (fetched) {
                        return Delivery.Status.READY;
                    }
                    break;
            }
        }
        return status;
    }
}
