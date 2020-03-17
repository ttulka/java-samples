package com.ttulka.samples.ddd.ecommerce.billing.payment.jdbc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ttulka.samples.ddd.ecommerce.billing.FindPayments;
import com.ttulka.samples.ddd.ecommerce.billing.payment.Money;
import com.ttulka.samples.ddd.ecommerce.billing.payment.Payment;
import com.ttulka.samples.ddd.ecommerce.billing.payment.PaymentId;
import com.ttulka.samples.ddd.ecommerce.billing.payment.ReferenceId;
import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class PaymentsJdbc implements FindPayments {

    private final @NonNull JdbcTemplate jdbcTemplate;
    private final @NonNull EventPublisher eventPublisher;

    @Override
    public List<Payment> all() {
        List<Map<String, Object>> deliveries = jdbcTemplate.queryForList(
                "SELECT id, reference_id referenceId, total, status FROM payments");
        return deliveries.stream()
                .map(payment -> new PaymentJdbc(
                        new PaymentId(payment.get("id")),
                        new ReferenceId(payment.get("referenceId")),
                        new Money(((BigDecimal) payment.get("total")).doubleValue()),
                        Enum.valueOf(Payment.Status.class, (String) payment.get("status")),
                        jdbcTemplate, eventPublisher))
                .collect(Collectors.toList());
    }
}
