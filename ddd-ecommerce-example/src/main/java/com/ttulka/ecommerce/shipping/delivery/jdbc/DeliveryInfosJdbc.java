package com.ttulka.ecommerce.shipping.delivery.jdbc;

import java.util.Map;
import java.util.stream.Stream;

import com.ttulka.ecommerce.shipping.delivery.DeliveryId;
import com.ttulka.ecommerce.shipping.delivery.DeliveryInfo;
import com.ttulka.ecommerce.shipping.delivery.DeliveryInfos;
import com.ttulka.ecommerce.shipping.delivery.OrderId;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;

/**
 * JDBC implementation of Categories collection.
 */
@Builder(access = AccessLevel.PRIVATE, toBuilder = true)
final class DeliveryInfosJdbc implements DeliveryInfos {

    private final @NonNull String query;

    private final @NonNull JdbcTemplate jdbcTemplate;

    public DeliveryInfosJdbc(@NonNull String query, @NonNull JdbcTemplate jdbcTemplate) {
        this.query = query;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Stream<DeliveryInfo> stream() {
        return jdbcTemplate.queryForList(query.concat(" ORDER BY 1"))
                .stream()
                .map(this::toDeliveryInfo);
    }

    private DeliveryInfo toDeliveryInfo(Map<String, Object> entry) {
        return new DeliveryInfo(
                new DeliveryId(entry.get("id")),
                new OrderId(entry.get("orderId")));
    }
}
