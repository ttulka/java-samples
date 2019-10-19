package com.ttulka.exercise;

public class InvalidLoginException extends AccountServiceException {

    public InvalidLoginException(String message) {
        super(message);
    }
}
