package com.ttulka.exercise;

public class UsernameAlreadyExistsException extends AccountServiceException {

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
