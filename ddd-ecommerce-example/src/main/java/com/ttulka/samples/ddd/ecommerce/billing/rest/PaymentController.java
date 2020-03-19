package com.ttulka.samples.ddd.ecommerce.billing.rest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.billing.FindPayments;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
class PaymentController {

    private final @NonNull FindPayments findPayments;

    @GetMapping
    public List<Map<String, ?>> all() {
        return findPayments.all().stream()
                .map(payment -> Map.of(
                        "id", payment.id().value(),
                        "referenceId", payment.referenceId().value(),
                        "total", payment.total().value(),
                        "collected", payment.isCollected()))
                .collect(Collectors.toList());
    }
}
