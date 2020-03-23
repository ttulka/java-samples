package com.ttulka.samples.ddd.ecommerce.billing;

import com.ttulka.samples.ddd.ecommerce.billing.payment.Money;
import com.ttulka.samples.ddd.ecommerce.billing.payment.ReferenceId;

public interface CollectPayment {

    /**
     * Collects a payment.
     *
     * @param referenceId the reference ID for the payment
     * @param total       the total amount of money to be collected
     */
    void collect(ReferenceId referenceId, Money total);
}
