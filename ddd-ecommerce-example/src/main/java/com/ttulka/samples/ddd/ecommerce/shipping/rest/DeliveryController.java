package com.ttulka.samples.ddd.ecommerce.shipping.rest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.shipping.FindDeliveries;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.Delivery;
import com.ttulka.samples.ddd.ecommerce.shipping.delivery.OrderId;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
class DeliveryController {

    private final @NonNull FindDeliveries findDeliveries;

    @GetMapping
    public List<Map<String, Object>> all() {
        return findDeliveries.all().stream()
                .map(delivery -> Map.of(
                        "id", delivery.id().value(),
                        "orderId", delivery.orderId().value()))
                .collect(Collectors.toList());
    }

    @GetMapping("{orderId}")
    public Map<String, Object> byOrder(@PathVariable @NonNull Object orderId) {
        Delivery delivery = findDeliveries.byOrderId(new OrderId(orderId));
        return Map.of(
                "id", delivery.id().value(),
                "address", Map.of(
                        "person", delivery.address().person().value(),
                        "place", delivery.address().place().value()),
                "items", delivery.items().stream()
                        .map(item -> Map.of(
                                "code", item.productCode().value(),
                                "quantity", item.quantity().value()))
                        .toArray(),
                "dispatched", delivery.isDispatched());
    }
}
