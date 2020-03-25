package com.ttulka.ecommerce.billing.payment;

public interface Payment {

    PaymentId id();

    ReferenceId referenceId();

    Money total();

    void request();

    void collect();

    boolean isRequested();

    boolean isCollected();

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
