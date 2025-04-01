package com.example.exception; 

public class InvalidMessageException extends RuntimeException {
    //Distinguishes Error type
    private String errorType; 

    //Basic error message for invalid message
    public InvalidMessageException (String errorType, String message) {
        super(message);
    }

    // Get the error type
    public String getErrorType() {
        return errorType;
    }
}