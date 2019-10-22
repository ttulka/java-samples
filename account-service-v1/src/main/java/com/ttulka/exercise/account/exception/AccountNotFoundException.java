package com.ttulka.exercise.account.exception;

public class AccountNotFoundException extends AccountServiceException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
