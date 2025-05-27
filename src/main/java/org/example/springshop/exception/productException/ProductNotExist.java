package org.example.springshop.exception.productException;

public class ProductNotExist extends RuntimeException {
    public ProductNotExist(String message) {
        super(message);
    }
}
