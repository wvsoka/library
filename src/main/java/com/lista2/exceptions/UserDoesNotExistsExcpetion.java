package com.lista2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for indicating that a user does not exist.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class UserDoesNotExistsExcpetion extends RuntimeException {

    /**
     * Constructs a new UserDoesNotExistsExcpetion with the specified user ID.
     *
     * @param userId The ID of the user that does not exist.
     */
    public UserDoesNotExistsExcpetion(Integer userId) {
        super("User with id '" + userId + "' does not exists.");
    }
}
