package com.ttulka.exercise.account.exception;

public class UsernameAlreadyExistsException extends AccountServiceException {

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
