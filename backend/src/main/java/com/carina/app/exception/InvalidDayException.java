package com.carina.app.exception;

public class InvalidDayException extends RuntimeException {

    private String message = "Invalid Day Input";

    public InvalidDayException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
