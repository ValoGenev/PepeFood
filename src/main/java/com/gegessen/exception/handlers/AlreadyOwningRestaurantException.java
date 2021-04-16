package com.gegessen.exception.handlers;

public class AlreadyOwningRestaurantException extends RuntimeException {

    public AlreadyOwningRestaurantException(String message) {
        super(message);
    }
}
