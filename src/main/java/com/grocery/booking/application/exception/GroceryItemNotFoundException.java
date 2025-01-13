package com.grocery.booking.application.exception;

public class GroceryItemNotFoundException extends RuntimeException {
    public GroceryItemNotFoundException(String message) {
        super(message);
    }
}