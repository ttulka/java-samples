package com.ttulka.samples.ddd.ecommerce.warehouse.jdbc;

import com.ttulka.samples.ddd.ecommerce.warehouse.OrderId;
import com.ttulka.samples.ddd.ecommerce.warehouse.RemoveFetchedGoods;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
final class RemoveFetchedGoodsJdbc implements RemoveFetchedGoods {

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public void forOrder(OrderId orderId) {
        // TODO remove fetched products

        log.info("Fetched goods removed... {}", orderId);
    }
}
