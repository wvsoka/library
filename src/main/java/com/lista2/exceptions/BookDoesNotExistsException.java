package com.lista2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for indicating that a book does not exist.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class BookDoesNotExistsException extends RuntimeException {

    /**
     * Constructs a new BookDoesNotExistsException with the specified detail message.
     *
     * @param message The detail message indicating the reason for the exception.
     */
    public BookDoesNotExistsException(String message) {
        super(message);
    }
}
