package com.ttulka.samples.ddd.ecommerce.shipping;

import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.billing.OrderPaid;
import com.ttulka.samples.ddd.ecommerce.sales.order.OrderPlaced;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Address;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DeliveryItem;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.DispatchDelivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Person;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Place;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.PrepareDelivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.ProductCode;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Quantity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import lombok.RequiredArgsConstructor;

@Configuration
class ShippingConfig {

    @Bean("shipping-orderPlacedListener")
    OrderPlacedListener orderPlacedListener(PrepareDelivery prepareDelivery) {
        return new OrderPlacedListener(prepareDelivery);
    }

    @Bean("shipping-orderPaidListener")
    OrderPaidListener orderPaidListener(DispatchDelivery dispatchDelivery) {
        return new OrderPaidListener(dispatchDelivery);
    }

    @RequiredArgsConstructor
    private static final class OrderPlacedListener {

        private final PrepareDelivery prepareDelivery;

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
    private static final class OrderPaidListener {

        private final DispatchDelivery dispatchDelivery;

        @EventListener
        public void on(OrderPaid event) {
            dispatchDelivery.byOrderId(new OrderId(event.orderId));
        }
    }
}
