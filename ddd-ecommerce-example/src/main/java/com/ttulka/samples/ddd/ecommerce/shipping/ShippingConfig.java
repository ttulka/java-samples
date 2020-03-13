package com.ttulka.samples.ddd.ecommerce.shipping;

import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.billing.PaymentReceived;
import com.ttulka.samples.ddd.ecommerce.sales.OrderPlaced;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Address;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryItem;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Person;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Place;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.ProductCode;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Quantity;
import com.ttulka.samples.ddd.ecommerce.warehouse.GoodsFetched;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Configuration
class ShippingConfig {

    @Bean("shipping-orderPlacedListener")
    OrderPlacedListener orderPlacedListener(PrepareDelivery prepareDelivery) {
        return new OrderPlacedListener(prepareDelivery);
    }

    @Bean("shipping-dispatchingListener")
    DispatchingListener dispatchingListener(UpdateDelivery updateDelivery) {
        return new DispatchingListener(updateDelivery);
    }

    @RequiredArgsConstructor
    private static final class OrderPlacedListener {

        private final @NonNull PrepareDelivery prepareDelivery;

        @EventListener
        @Async
        @Order(10)
        public void on(OrderPlaced event) {
            prepareDelivery.forOrder(
                    new OrderId(event.orderId),
                    event.orderItems.stream()
                            .map(item -> new DeliveryItem(
                                    new ProductCode(item.code),
                                    new Quantity(item.quantity)))
                            .collect(Collectors.toList()),
                    new Address(
                            new Person(event.customer.name),
                            new Place(event.customer.address)));
        }
    }

    @RequiredArgsConstructor
    private static final class DispatchingListener {

        private final @NonNull UpdateDelivery updateDelivery;

        @EventListener
        public void on(GoodsFetched event) {
            updateDelivery.asFetched(new OrderId(event.orderId));
        }

        @EventListener
        public void on(PaymentReceived event) {
            updateDelivery.asPaid(new OrderId(event.referenceId));
        }
    }
}
