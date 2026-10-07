package com.devsu.hackerearth.backend.account.exception;

public class InsufficientBalanceException extends RuntimeException {
    
    public InsufficientBalanceException(String message) {
        super(message);
    }
    
}