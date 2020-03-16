package com.ttulka.samples.ddd.ecommerce.billing.payment.jdbc;

import com.ttulka.samples.ddd.ecommerce.billing.PaymentReceived;
import com.ttulka.samples.ddd.ecommerce.billing.payment.Money;
import com.ttulka.samples.ddd.ecommerce.billing.payment.Payment;
import com.ttulka.samples.ddd.ecommerce.billing.payment.ReferenceId;
import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;
import com.ttulka.samples.ddd.ecommerce.warehouse.GoodsFetched;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@JdbcTest
class PaymentTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void payment_values() {
        Payment payment = new PaymentJdbc(
                new ReferenceId(123L), new Money(123.5), jdbcTemplate, eventPublisher);
        assertAll(
                () -> assertThat(payment.id()).isNotNull(),
                () -> assertThat(payment.referenceId()).isEqualTo(new ReferenceId(123L)),
                () -> assertThat(payment.total()).isEqualTo(new Money(123.5))
        );
    }

    @Test
    void payment_is_collected() {
        Payment payment = new PaymentJdbc(
                new ReferenceId(123L), new Money(123.5), jdbcTemplate, eventPublisher);
        payment.collect();

        assertThat(payment.isCollected()).isTrue();
    }

    @Test
    void payment_is_confirmed() {
        Payment payment = new PaymentJdbc(
                new ReferenceId(123L), new Money(123.5), jdbcTemplate, eventPublisher);
        payment.collect();
        payment.confirm();

        assertThat(payment.isConfirmed()).isTrue();
    }

    @Test
    void confirmed_payment_raises_an_event() {
        Payment payment = new PaymentJdbc(
                new ReferenceId(123L), new Money(123.5), jdbcTemplate, eventPublisher);
        payment.collect();
        payment.confirm();

        verify(eventPublisher).raise(argThat(
                event -> {
                    assertThat(event).isInstanceOf(PaymentReceived.class);
                    PaymentReceived paymentReceived = (PaymentReceived) event;
                    assertAll(
                            () -> assertThat(paymentReceived.when).isNotNull(),
                            () -> assertThat(paymentReceived.referenceId).isEqualTo(123L)
                    );
                    return true;
                }));
    }

    @Test
    void cannot_collect_already_collected_payment() {
        Payment payment = new PaymentJdbc(
                new ReferenceId(123L), new Money(123.5), jdbcTemplate, eventPublisher);
        payment.collect();

        assertThrows(Payment.PaymentAlreadyRequestedException.class, () -> payment.collect());
    }

    @Test
    void cannot_collect_already_confirmed_payment() {
        Payment payment = new PaymentJdbc(
                new ReferenceId(123L), new Money(123.5), jdbcTemplate, eventPublisher);
        payment.collect();
        payment.confirm();

        assertThrows(Payment.PaymentAlreadyRequestedException.class, () -> payment.collect());
    }

    @Test
    void cannot_confirm_unrequested_payment() {
        Payment payment = new PaymentJdbc(
                new ReferenceId(123L), new Money(123.5), jdbcTemplate, eventPublisher);

        assertThrows(Payment.PaymentNotRequestedYetException.class, () -> payment.confirm());
    }

    @Test
    void cannot_confirm_already_confirmed_payment() {
        Payment payment = new PaymentJdbc(
                new ReferenceId(123L), new Money(123.5), jdbcTemplate, eventPublisher);
        payment.collect();
        payment.confirm();

        assertThrows(Payment.PaymentAlreadyReceivedException.class, () -> payment.confirm());
    }
}
