package com.example.svt2023sr50.exceptionhandling.exception;

public class MalformedQueryException extends RuntimeException {

    public MalformedQueryException(String message) {
        super(message);
    }
}
