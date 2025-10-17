package com.kuza.kuzasokoni.domain.client.exceptions;


public class ClientAlreadyExistsException extends RuntimeException {
    public ClientAlreadyExistsException(String phoneNumber) {
        super("Client with phone number " + phoneNumber + " already exists.");
    }
}
