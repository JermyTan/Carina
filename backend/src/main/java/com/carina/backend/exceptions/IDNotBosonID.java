package com.carina.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class IDNotBosonID extends RuntimeException {
    public IDNotBosonID(String exception) {
        super(exception);
    }
}
