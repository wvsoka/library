package com.lista2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for indicating that a loan already exists.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class LoanAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new LoanAlreadyExistsException with the specified detail message.
     *
     * @param message The detail message indicating the reason for the exception.
     */
    public LoanAlreadyExistsException(String message) {
        super(message);
    }
}
