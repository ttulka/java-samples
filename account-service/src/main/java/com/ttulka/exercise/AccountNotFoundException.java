package com.ttulka.exercise;

public class AccountNotFoundException extends AccountServiceException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
