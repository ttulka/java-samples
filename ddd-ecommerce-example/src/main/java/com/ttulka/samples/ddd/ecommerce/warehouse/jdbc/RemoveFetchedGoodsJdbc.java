package com.ttulka.samples.ddd.ecommerce.warehouse.jdbc;

import com.ttulka.samples.ddd.ecommerce.warehouse.OrderId;
import com.ttulka.samples.ddd.ecommerce.warehouse.RemoveFetchedGoods;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
class RemoveFetchedGoodsJdbc implements RemoveFetchedGoods {

    private final @NonNull JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void forOrder(OrderId orderId) {
        jdbcTemplate.update(
                "DELETE FROM fetched_products WHERE order_id = ?",
                orderId.value());

        log.info("Fetched goods removed... {}", orderId);
    }
}
