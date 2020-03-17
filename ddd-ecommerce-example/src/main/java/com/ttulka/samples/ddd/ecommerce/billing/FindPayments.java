package com.ttulka.samples.ddd.ecommerce.billing;

import java.util.List;

import com.ttulka.samples.ddd.ecommerce.billing.payment.Payment;

public interface FindPayments {

    List<Payment> all();
}
