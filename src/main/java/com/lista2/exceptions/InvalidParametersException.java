package com.lista2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for indicating that invalid parameters were provided.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidParametersException extends RuntimeException {

    /**
     * Constructs a new InvalidParametersException with the specified detail message.
     *
     * @param message The detail message indicating the reason for the exception.
     */
    public InvalidParametersException(String message) {
        super(message);
    }
}
