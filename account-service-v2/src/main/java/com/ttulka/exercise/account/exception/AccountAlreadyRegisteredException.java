package com.ttulka.exercise.account.exception;

public class AccountAlreadyRegisteredException extends AccountServiceException {

    public AccountAlreadyRegisteredException(String message) {
        super(message);
    }
}
