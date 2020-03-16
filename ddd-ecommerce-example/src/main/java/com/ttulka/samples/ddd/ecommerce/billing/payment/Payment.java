package com.ttulka.samples.ddd.ecommerce.billing.payment;

public interface Payment {

    PaymentId id();

    ReferenceId referenceId();

    Money total();

    void collect();

    void confirm();

    enum Status {
        NEW, REQUESTED, RECEIVED
    }

    final class PaymentAlreadyRequestedException extends IllegalStateException {
    }

    final class PaymentNotRequestedYetException extends IllegalStateException {
    }

    final class PaymentAlreadyReceivedException extends IllegalStateException {
    }
}
