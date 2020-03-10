package com.ttulka.samples.ddd.ecommerce.shipping;

import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.billing.OrderPaid;
import com.ttulka.samples.ddd.ecommerce.sales.order.OrderPlaced;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;

import lombok.RequiredArgsConstructor;

@Configuration
class ShippingConfig {

    @Bean("shipping-orderPlacedListener")
    OrderPlacedListener orderPlacedListener(PrepareDelivery prepareDelivery) {
        return new OrderPlacedListener(prepareDelivery);
    }

    @Bean("shipping-orderPaidListener")
    OrderPaidListener orderPaidListener(ShipDelivery shipDelivery) {
        return new OrderPaidListener(shipDelivery);
    }

    @RequiredArgsConstructor
    private static final class OrderPlacedListener {

        private final PrepareDelivery prepareDelivery;

        @EventListener
        @Order(10)
        public void on(OrderPlaced event) {
            prepareDelivery.forOrder(
                    new OrderId(event.orderId),
                    event.orderItems.stream()
                            .map(item -> new DeliveryItem(
                                    new ProductCode(item.code),
                                    new Amount(item.amount)))
                            .collect(Collectors.toList()),
                    new Address(
                            new Person(event.customer.name),
                            new Place(event.customer.address)));
        }
    }

    @RequiredArgsConstructor
    private static final class OrderPaidListener {

        private final ShipDelivery shipDelivery;

        @EventListener
        public void on(OrderPaid event) {
            shipDelivery.byOrderId(new OrderId(event.orderId));
        }
    }
}
