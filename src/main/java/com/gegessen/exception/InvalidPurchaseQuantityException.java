package com.gegessen.exception;

public class InvalidPurchaseQuantityException extends RuntimeException {

    public InvalidPurchaseQuantityException(String message) {
        super(message);
    }
}
