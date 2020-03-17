package com.ttulka.samples.ddd.ecommerce.billing.payment.jdbc;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.billing.FindPayments;
import com.ttulka.samples.ddd.ecommerce.billing.payment.Money;
import com.ttulka.samples.ddd.ecommerce.billing.payment.Payment;
import com.ttulka.samples.ddd.ecommerce.billing.payment.PaymentId;
import com.ttulka.samples.ddd.ecommerce.billing.payment.ReferenceId;
import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@ContextConfiguration(classes = PaymentJdbcConfig.class)
@Sql("/test-data-billing-find-payments.sql")
class FindPaymentsTest {

    @Autowired
    private FindPayments findPayments;

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void all_payments_are_found() {
        List<Payment> payments = findPayments.all();
        assertAll(
                () -> assertThat(payments).hasSize(2),
                () -> assertThat(payments.get(0).id()).isEqualTo(new PaymentId(101L)),
                () -> assertThat(payments.get(0).referenceId()).isEqualTo(new ReferenceId(1001L)),
                () -> assertThat(payments.get(0).total()).isEqualTo(new Money(100.5)),
                () -> assertThat(payments.get(0).isCollected()).isTrue(),
                () -> assertThat(payments.get(0).isConfirmed()).isFalse(),
                () -> assertThat(payments.get(1).id()).isEqualTo(new PaymentId(102L)),
                () -> assertThat(payments.get(1).referenceId()).isEqualTo(new ReferenceId(1002L)),
                () -> assertThat(payments.get(1).total()).isEqualTo(new Money(200.5)),
                () -> assertThat(payments.get(1).isCollected()).isTrue(),
                () -> assertThat(payments.get(1).isConfirmed()).isTrue()
        );
    }
}
