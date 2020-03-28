package com.ttulka.ecommerce.billing.payment;

/**
 * Payment entity.
 */
public interface Payment {

    PaymentId id();

    ReferenceId referenceId();

    Money total();

    void request();

    void collect();

    boolean isRequested();

    boolean isCollected();

    /**
     * PaymentAlreadyRequestedException is thrown when an already requested Payment is requested.
     */
    final class PaymentAlreadyRequestedException extends IllegalStateException {
    }

    /**
     * PaymentNotRequestedYetException is thrown when a Payment is collected but not requested yet.
     */
    final class PaymentNotRequestedYetException extends IllegalStateException {
    }

    /**
     * PaymentAlreadyReceivedException is thrown when an already collected Payment is collected.
     */
    final class PaymentAlreadyReceivedException extends IllegalStateException {
    }
}
