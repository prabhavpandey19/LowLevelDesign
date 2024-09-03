package com.example.upiflipkartintegration.exception;

public class BankNotActiveException extends RuntimeException{
    public BankNotActiveException(String message) {
        super(message);
    }
}
