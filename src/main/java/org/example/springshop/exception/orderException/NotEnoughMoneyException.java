package org.example.springshop.exception.orderException;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException() {
        super("user not found");
    }


}
