package com.lista2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Exception class for indicating that a user already exists.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new UserAlreadyExistsException with the specified login.
     *
     * @param login The login of the user that already exists.
     */
    public UserAlreadyExistsException(String login) {
        super("User with login '" + login + "' already exists.");
    }
}
