package com.lista2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for indicating that no loans are available.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class NoLoansException extends RuntimeException {

    /**
     * Constructs a new NoLoansException with the specified detail message.
     *
     * @param message The detail message indicating the reason for the exception.
     */
    public NoLoansException(String message) {
        super(message);
    }
}
