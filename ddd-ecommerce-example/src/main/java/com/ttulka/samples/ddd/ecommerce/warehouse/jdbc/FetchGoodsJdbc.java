package com.ttulka.samples.ddd.ecommerce.warehouse.jdbc;

import java.time.Instant;
import java.util.Collection;

import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.warehouse.FetchGoods;
import com.ttulka.samples.ddd.ecommerce.warehouse.GoodsFetched;
import com.ttulka.samples.ddd.ecommerce.warehouse.GoodsMissed;
import com.ttulka.samples.ddd.ecommerce.warehouse.InStock;
import com.ttulka.samples.ddd.ecommerce.warehouse.OrderId;
import com.ttulka.samples.ddd.ecommerce.warehouse.ToFetch;
import com.ttulka.samples.ddd.ecommerce.warehouse.Warehouse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
class FetchGoodsJdbc implements FetchGoods {

    private final @NonNull Warehouse warehouse;
    private final @NonNull JdbcTemplate jdbcTemplate;
    private final @NonNull EventPublisher eventPublisher;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void fromOrder(OrderId orderId, Collection<ToFetch> toFetch) {
        toFetch.forEach(item -> fetch(item, orderId));

        eventPublisher.raise(new GoodsFetched(Instant.now(), orderId.value()));

        log.info("Goods fetched... {}", toFetch);
    }

    private void fetch(ToFetch item, OrderId orderId) {
        InStock inStock = warehouse.leftInStock(item.productCode());
        if (!inStock.has(item.amount())) {
            eventPublisher.raise(new GoodsMissed(
                    Instant.now(), item.productCode().value(), inStock.needsYet(item.amount()).value()));

        } else {
            jdbcTemplate.update(
                    "INSERT INTO fetched_products VALUES (?, ?, ?)",
                    item.productCode().value(), item.amount().value(), orderId.value());

            jdbcTemplate.update(
                    "UPDATE products_in_stock SET amount = amount - ? WHERE product_code = ?",
                    item.amount().value(), item.productCode().value());
        }
    }
}
