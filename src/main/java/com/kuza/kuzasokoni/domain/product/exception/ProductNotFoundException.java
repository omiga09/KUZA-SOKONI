package com.kuza.kuzasokoni.domain.product.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Product with ID " + id + " not found.");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
