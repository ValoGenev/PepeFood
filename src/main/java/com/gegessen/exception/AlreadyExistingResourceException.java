package com.gegessen.exception;

public class AlreadyExistingResourceException extends RuntimeException {

    public AlreadyExistingResourceException(String message) {
        super(message);
    }
}
