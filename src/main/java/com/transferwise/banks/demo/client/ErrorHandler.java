package com.transferwise.banks.demo.client;

import com.transferwise.banks.demo.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorHandler {

<<<<<<< HEAD
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ApiError> handleWebClientResponseException(final WebClientResponseException ex) {
        return new ResponseEntity(new ApiError(ex), ex.getStatusCode());
=======
    //@ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleCustomException(final ResponseStatusException cause) {
        return new ResponseEntity(new ApiError(cause), cause.getStatus());
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ApiError> handleWebClientResponseException(final WebClientResponseException ex) {
        return new ResponseEntity(new ApiError(ex), ex.getStatusCode());
        //return ResponseEntity.status(ex.getRawStatusCode()).body(ex.getResponseBodyAsString());
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(final ResourceNotFoundException ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 80005fdfa15e98a1dde8987b902e5886f92589cc
