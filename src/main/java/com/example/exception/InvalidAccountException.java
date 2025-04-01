package com.example.exception;

public class InvalidAccountException extends RuntimeException {
    //Distinguishes Error type
    private String errorType; 
    
    // Constructor for the exception with error message and the type beuing either username, password, or login
    public InvalidAccountException(String errorType, String message) {
        super(message); 
        this.errorType = errorType;  
    }
    // Get the error type
    public String getErrorType() {
        return errorType;
    }
}