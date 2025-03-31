package com.example.exception;

public class InvalidAccountException extends RuntimeException {
    
    private String errorType; // To distinguish between different types of errors (username/password)
    
    // Constructor for the exception with error message and type
    public InvalidAccountException(String errorType, String message) {
        super(message); 
        this.errorType = errorType;  // Set the error type (either "username" or "password")
    }

    // Get the error type (username or password)
    public String getErrorType() {
        return errorType;
    }
}