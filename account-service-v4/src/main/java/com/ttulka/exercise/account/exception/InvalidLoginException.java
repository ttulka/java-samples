package com.ttulka.exercise.account.exception;

public class InvalidLoginException extends AccountServiceException {

    public InvalidLoginException(String message) {
        super(message);
    }
}
