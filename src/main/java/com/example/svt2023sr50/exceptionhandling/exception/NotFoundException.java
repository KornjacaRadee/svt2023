package com.example.svt2023sr50.exceptionhandling.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
