package com.ttulka.ecommerce.billing;

import java.util.List;

import com.ttulka.ecommerce.billing.payment.Payment;

/**
 * Find Payments use-case.
 */
public interface FindPayments {

    /**
     * Finds all payments.
     *
     * @return all payments
     */
    List<Payment> all();
}
