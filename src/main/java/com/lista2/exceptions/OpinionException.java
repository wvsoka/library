package com.lista2.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for indicating an error related to opinions.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class OpinionException extends RuntimeException {

    /**
     * Constructs a new OpinionException with the specified detail message.
     *
     * @param message The detail message indicating the reason for the exception.
     */
    public OpinionException(String message) {
        super(message);
    }
}
