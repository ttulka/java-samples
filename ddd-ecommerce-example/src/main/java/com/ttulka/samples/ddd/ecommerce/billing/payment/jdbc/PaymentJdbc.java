package com.ttulka.samples.ddd.ecommerce.billing.payment.jdbc;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

import com.ttulka.samples.ddd.ecommerce.billing.PaymentReceived;
import com.ttulka.samples.ddd.ecommerce.billing.payment.Money;
import com.ttulka.samples.ddd.ecommerce.billing.payment.Payment;
import com.ttulka.samples.ddd.ecommerce.billing.payment.PaymentId;
import com.ttulka.samples.ddd.ecommerce.billing.payment.ReferenceId;
import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;

import org.springframework.jdbc.core.JdbcTemplate;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(of = "id")
@ToString(of = {"referenceId", "total"})
@Slf4j
final class PaymentJdbc implements Payment {

    private static final AtomicLong idSequence = new AtomicLong(); // TODO

    private final @NonNull PaymentId id;
    private final @NonNull ReferenceId referenceId;
    private final @NonNull Money total;

    private final @NonNull JdbcTemplate jdbcTemplate;
    private final @NonNull EventPublisher eventPublisher;

    private @NonNull Status status = Status.NEW;

    public PaymentJdbc(@NonNull PaymentId id, @NonNull ReferenceId referenceId, @NonNull Money total, @NonNull Status status,
                       @NonNull JdbcTemplate jdbcTemplate, @NonNull EventPublisher eventPublisher) {
        this.id = id;
        this.referenceId = referenceId;
        this.total = total;
        this.status = status;
        this.jdbcTemplate = jdbcTemplate;
        this.eventPublisher = eventPublisher;
    }

    public PaymentJdbc(@NonNull ReferenceId referenceId, @NonNull Money total, @NonNull JdbcTemplate jdbcTemplate, @NonNull EventPublisher eventPublisher) {
        this(new PaymentId(idSequence.incrementAndGet()), referenceId, total, Status.NEW, jdbcTemplate, eventPublisher);
    }

    @Override
    public PaymentId id() {
        return id;
    }

    @Override
    public ReferenceId referenceId() {
        return referenceId;
    }

    @Override
    public Money total() {
        return total;
    }

    @Override
    public void collect() {
        if (isCollected() || isConfirmed()) {
            throw new PaymentAlreadyRequestedException();
        }
        status = Status.REQUESTED;

        jdbcTemplate.update(
                "INSERT INTO payments VALUES (?, ?, ?, ?)",
                id.value(), referenceId.value(), total.value(), status.name());

        log.info("Payment collected: {}", this);
    }

    @Override
    public void confirm() {
        if (isConfirmed()) {
            throw new PaymentAlreadyReceivedException();
        }
        if (!isCollected()) {
            throw new PaymentNotRequestedYetException();
        }
        status = Status.RECEIVED;

        jdbcTemplate.update(
                "UPDATE payments SET status = ? WHERE id = ?",
                status.name(), id.value());

        eventPublisher.raise(new PaymentReceived(Instant.now(), referenceId.value()));

        log.info("Payment confirmed: {}", this);
    }

    @Override
    public boolean isCollected() {
        return Status.REQUESTED == status || Status.RECEIVED == status;
    }

    @Override
    public boolean isConfirmed() {
        return Status.RECEIVED == status;
    }
}
