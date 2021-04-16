package com.gegessen.exception;

import org.aspectj.apache.bcel.classfile.annotation.RuntimeTypeAnnos;

public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
