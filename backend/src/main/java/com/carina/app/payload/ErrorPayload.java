package com.carina.app.payload;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorPayload {

    private HttpStatus httpStatus;

    private LocalDateTime timestamp;

    private String message;

    private String details;

    private int code;

    /**
     * Creates instance for error payload.
     * @param httpStatus http status.
     * @param message error message.
     * @param details error details.
     */
    public ErrorPayload(HttpStatus httpStatus, String message, String details) {
        this.httpStatus = httpStatus;
        this.code = httpStatus.value();
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.details = details;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public int getCode() {
        return code;
    }
}