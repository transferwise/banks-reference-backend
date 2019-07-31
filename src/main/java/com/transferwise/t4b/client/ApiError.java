package com.transferwise.t4b.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

public class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;

    public final HttpStatus status;
    public final String message;

    public ApiError(final ResponseStatusException cause) {
        timestamp = LocalDateTime.now();
        status = cause.getStatus();
        message = cause.getReason();
    }

    public ApiError(final WebClientResponseException cause) {
        timestamp = LocalDateTime.now();
        status = cause.getStatusCode();
        message = cause.getResponseBodyAsString();
    }
}
