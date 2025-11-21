package com.kuza.kuzasokoni.domain.authentication.exception;


    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
}
