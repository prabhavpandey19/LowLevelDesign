package com.example.versionManagement.exception;

public class NotSupportedVersionException extends RuntimeException{
    public NotSupportedVersionException() {
        super();
    }

    public NotSupportedVersionException(String message) {
        super(message);
    }
}
