package com.ttulka.samples.ddd.ecommerce.billing;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.billing.payment.Payment;

public interface FindPayments {

    /**
     * Finds all payments.
     *
     * @return all payments
     */
    List<Payment> all();
}
