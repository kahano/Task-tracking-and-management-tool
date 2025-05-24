package com.kahano.Task.tracking.and.management.tool.Controllers;

import com.kahano.Task.tracking.and.management.tool.domain.DTO.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleExceptions(
            RuntimeException e, WebRequest req) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                req.getDescription(false)
        );

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}

