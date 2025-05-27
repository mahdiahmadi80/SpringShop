package org.example.springshop.exception.orderException;

public class OrdetNotFoundException extends RuntimeException {
    public OrdetNotFoundException(String message) {
        super(message);
    }
}
