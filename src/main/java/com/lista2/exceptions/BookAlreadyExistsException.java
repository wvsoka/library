package com.lista2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for indicating that a book already exists.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class BookAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new BookAlreadyExistsException with the specified book ID.
     *
     * @param id The ID of the book that already exists.
     */
    public BookAlreadyExistsException(Integer id) {
        super("Book with id '" + id + "' already exists.");
    }
}
