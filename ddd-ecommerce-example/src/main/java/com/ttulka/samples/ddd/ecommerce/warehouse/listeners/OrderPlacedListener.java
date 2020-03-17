package com.ttulka.samples.ddd.ecommerce.warehouse.listeners;

import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.sales.OrderPlaced;
import com.ttulka.samples.ddd.ecommerce.warehouse.Amount;
import com.ttulka.samples.ddd.ecommerce.warehouse.FetchGoods;
import com.ttulka.samples.ddd.ecommerce.warehouse.OrderId;
import com.ttulka.samples.ddd.ecommerce.warehouse.ProductCode;
import com.ttulka.samples.ddd.ecommerce.warehouse.ToFetch;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class OrderPlacedListener {

    private final @NonNull FetchGoods fetchGoods;

    @TransactionalEventListener
    @Async
    public void on(OrderPlaced event) {
        fetchGoods.fromOrder(
                new OrderId(event.orderId),
                event.orderItems.stream()
                        .map(item -> new ToFetch(new ProductCode(item.code), new Amount(item.quantity)))
                        .collect(Collectors.toList()));
    }
}