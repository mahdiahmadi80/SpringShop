package org.example.springshop.exception.userException;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super("user not found");
    }
}
