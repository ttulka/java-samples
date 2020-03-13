package com.ttulka.samples.ddd.ecommerce.billing.payment;

import com.ttulka.samples.ddd.ecommerce.billing.PaymentReceived;
import com.ttulka.samples.ddd.ecommerce.common.EventPublisher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class ConfirmPaymentTest {

    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void payment_confirmation_raises_an_event() {
        new Payment(new ReferenceId(123L), new Money(123.5), eventPublisher)
                .confirm();

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
}