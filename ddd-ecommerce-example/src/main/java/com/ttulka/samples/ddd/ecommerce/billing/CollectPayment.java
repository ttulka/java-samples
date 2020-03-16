package com.ttulka.samples.ddd.ecommerce.billing;

import com.ttulka.samples.ddd.ecommerce.billing.payment.Money;
import com.ttulka.samples.ddd.ecommerce.billing.payment.ReferenceId;

public interface CollectPayment {

    void collect(ReferenceId referenceId, Money total);
}
