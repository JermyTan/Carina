package com.carina.app.exception;

import com.carina.app.payload.ErrorPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidDayException.class)
    private ResponseEntity<ErrorPayload> validationError(InvalidDayException ex){
        ErrorPayload error = new ErrorPayload(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid day", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
