package com.ttulka.ecommerce.billing.payment.jdbc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ttulka.ecommerce.billing.CollectPayment;
import com.ttulka.ecommerce.billing.FindPayments;
import com.ttulka.ecommerce.billing.payment.Money;
import com.ttulka.ecommerce.billing.payment.Payment;
import com.ttulka.ecommerce.billing.payment.PaymentId;
import com.ttulka.ecommerce.billing.payment.ReferenceId;
import com.ttulka.ecommerce.common.EventPublisher;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Implementation for Payment use-cases.
 */
@RequiredArgsConstructor
class PaymentsJdbc implements FindPayments, CollectPayment {

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
                        Enum.valueOf(PaymentJdbc.Status.class, (String) payment.get("status")),
                        jdbcTemplate, eventPublisher))
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void collect(ReferenceId referenceId, Money total) {
        Payment payment = new PaymentJdbc(referenceId, total, jdbcTemplate, eventPublisher);
        payment.request();
        payment.collect();
    }
}
