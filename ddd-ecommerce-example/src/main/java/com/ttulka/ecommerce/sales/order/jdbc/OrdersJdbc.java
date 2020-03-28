package com.ttulka.ecommerce.sales.order.jdbc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ttulka.ecommerce.common.EventPublisher;
import com.ttulka.ecommerce.sales.FindOrders;
import com.ttulka.ecommerce.sales.PlaceOrder;
import com.ttulka.ecommerce.sales.order.Order;
import com.ttulka.ecommerce.sales.order.OrderId;
import com.ttulka.ecommerce.sales.order.OrderItem;
import com.ttulka.ecommerce.sales.order.UnknownOrder;
import com.ttulka.ecommerce.sales.order.customer.Address;
import com.ttulka.ecommerce.sales.order.customer.Customer;
import com.ttulka.ecommerce.sales.order.customer.Name;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for Order use-cases.
 */
@RequiredArgsConstructor
@Slf4j
class OrdersJdbc implements FindOrders, PlaceOrder {

    private final @NonNull JdbcTemplate jdbcTemplate;
    private final @NonNull EventPublisher eventPublisher;

    @Override
    public Order byId(OrderId id) {
        try {
            Map<String, Object> order = jdbcTemplate.queryForMap(
                    "SELECT id, customer, address FROM orders WHERE id = ?",
                    id.value());

            List<Map<String, Object>> items = jdbcTemplate.queryForList(
                    "SELECT product_code code, title, price, quantity FROM order_items WHERE order_id = ?",
                    id.value());

            if (order != null && items != null) {
                return toOrder(order, items);
            }
        } catch (DataAccessException ignore) {
            log.warn("Order by ID {} was not found.", id);
        }
        return new UnknownOrder();
    }

    private Order toOrder(Map<String, Object> order, List<Map<String, Object>> items) {
        return new OrderJdbc(
                new OrderId(order.get("id")),
                items.stream()
                        .map(this::toOrderItem)
                        .collect(Collectors.toList()),
                new Customer(
                        new Name((String) order.get("customer")),
                        new Address((String) order.get("address"))),
                jdbcTemplate,
                eventPublisher);
    }

    private OrderItem toOrderItem(Map<String, Object> item) {
        return new OrderItem(
                (String) item.get("code"),
                (String) item.get("title"),
                ((BigDecimal) item.get("price")).floatValue(),
                (Integer) item.get("quantity"));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void place(@NonNull List<OrderItem> items, @NonNull Customer customer) {
        new OrderJdbc(items, customer, jdbcTemplate, eventPublisher)
                .place();
    }
}
