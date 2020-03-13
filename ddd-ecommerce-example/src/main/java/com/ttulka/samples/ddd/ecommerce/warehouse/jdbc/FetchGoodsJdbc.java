package com.ttulka.samples.ddd.ecommerce.warehouse.jdbc;

import java.time.Instant;
import java.util.Collection;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.warehouse.FetchGoods;
import com.ttulka.samples.ddd.ecommerce.warehouse.GoodsFetched;
import com.ttulka.samples.ddd.ecommerce.warehouse.OrderId;
import com.ttulka.samples.ddd.ecommerce.warehouse.ToFetch;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
final class FetchGoodsJdbc implements FetchGoods {

    private final @NonNull JdbcTemplate jdbcTemplate;
    private final @NonNull EventPublisher eventPublisher;

    @Override
    public void fromOrder(OrderId orderId, Collection<ToFetch> toFetch) {
        // TODO fetch and remove from the stock

        eventPublisher.raise(new GoodsFetched(Instant.now(), orderId.value()));

        log.info("Goods fetched... {}", toFetch);
    }
}
