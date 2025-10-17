package com.kuza.kuzasokoni.domain.product.exception;

public class ChargeNotFoundException extends RuntimeException {
    public ChargeNotFoundException(Long id) {
        super("Charge not found with id: " + id);
    }
}
