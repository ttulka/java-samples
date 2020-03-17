package com.ttulka.samples.ddd.ecommerce.billing.rest;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.billing.FindPayments;
import com.ttulka.samples.ddd.ecommerce.billing.payment.Money;
import com.ttulka.samples.ddd.ecommerce.billing.payment.Payment;
import com.ttulka.samples.ddd.ecommerce.billing.payment.PaymentId;
import com.ttulka.samples.ddd.ecommerce.billing.payment.ReferenceId;
import com.ttulka.samples.ddd.ecommerce.catalogue.Catalogue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindPayments findPayments;
    @MockBean
    private Catalogue catalogue;

    @Test
    void all_payments() throws Exception {
        when(findPayments.all()).thenReturn(List.of(
                testPayment(new PaymentId(123L), new ReferenceId("TEST-1"), new Money(456.5))));

        mockMvc.perform(get("/payment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(123)))
                .andExpect(jsonPath("$[0].referenceId", is("TEST-1")))
                .andExpect(jsonPath("$[0].total", is(456.5)));
    }

    private Payment testPayment(PaymentId id, ReferenceId referenceId, Money total) {
        return new Payment() {
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

            }

            @Override
            public void confirm() {

            }

            @Override
            public boolean isCollected() {
                return false;
            }

            @Override
            public boolean isConfirmed() {
                return false;
            }
        };
    }
}
