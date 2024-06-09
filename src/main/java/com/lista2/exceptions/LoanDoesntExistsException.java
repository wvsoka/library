package com.lista2.exceptions;

import com.lista2.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for indicating that a loan does not exist.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class LoanDoesntExistsException extends RuntimeException {

    /**
     * Constructs a new LoanDoesntExistsException with the specified detail message.
     *
     * @param message The detail message indicating the reason for the exception.
     */
    public LoanDoesntExistsException(String message) {
        super(message);
    }
}
