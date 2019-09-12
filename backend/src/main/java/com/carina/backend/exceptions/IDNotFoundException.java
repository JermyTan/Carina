package com.carina.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IDNotFoundException extends RuntimeException {
    public IDNotFoundException(String exception) {
        super(exception);
    }
}
