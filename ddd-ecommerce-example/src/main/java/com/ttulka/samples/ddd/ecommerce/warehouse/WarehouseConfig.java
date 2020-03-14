package com.ttulka.samples.ddd.ecommerce.warehouse;

import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.sales.OrderPlaced;
import com.ttulka.samples.ddd.ecommerce.shipping.DeliveryDispatched;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Configuration
class WarehouseConfig {

    @Bean("warehouse-orderPlacedListener")
    OrderPlacedListener orderPlacedListener(FetchGoods fetchGoods) {
        return new OrderPlacedListener(fetchGoods);
    }

    @Bean("warehouse-deliveryDispatchedListener")
    DeliveryDispatchedListener deliveryDispatchedListener(RemoveFetchedGoods removeFetchedGoods) {
        return new DeliveryDispatchedListener(removeFetchedGoods);
    }

    @RequiredArgsConstructor
    private static final class OrderPlacedListener {

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

    @RequiredArgsConstructor
    private static final class DeliveryDispatchedListener {

        private final @NonNull RemoveFetchedGoods removeFetchedGoods;

        @TransactionalEventListener
        @Async
        public void on(DeliveryDispatched event) {
            removeFetchedGoods.forOrder(new OrderId(event.orderId));
        }
    }
}
