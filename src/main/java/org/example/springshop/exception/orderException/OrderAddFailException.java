package org.example.springshop.exception.orderException;

public class OrderAddFailException extends RuntimeException {
    public OrderAddFailException(String message) {
        super(message);
    }
}
