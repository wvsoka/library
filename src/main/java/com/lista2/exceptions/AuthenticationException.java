package com.lista2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for authentication errors.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class AuthenticationException extends RuntimeException {

    /**
     * Constructs a new AuthenticationException with the specified detail message.
     *
     * @param message The detail message.
     */
    public AuthenticationException(String message) {
        super(message);
    }
}
