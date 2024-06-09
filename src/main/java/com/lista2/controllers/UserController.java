package com.lista2.controllers;

import com.lista2.User;
import com.lista2.exceptions.InvalidParametersException;
import com.lista2.exceptions.UserAlreadyExistsException;
import com.lista2.exceptions.UserDoesNotExistsExcpetion;
import com.lista2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing users.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Adds a new user.
     *
     * @param user The user to add.
     * @return ResponseEntity indicating the result of the operation.
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    /**
     * Deletes a user by its ID.
     *
     * @param userId The ID of the user to delete.
     * @return ResponseEntity indicating the result of the operation.
     */
    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        return userService.deleteUser(userId);
    }

    /**
     * Retrieves all users.
     *
     * @return Iterable containing all users.
     */
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Exception handler for UserAlreadyExistsException.
     *
     * @param e The exception instance.
     * @return ResponseEntity with the exception message and HTTP status code.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Exception handler for UserDoesNotExistsExcpetion.
     *
     * @param e The exception instance.
     * @return ResponseEntity with the exception message and HTTP status code.
     */
    @ExceptionHandler(UserDoesNotExistsExcpetion.class)
    public ResponseEntity<String> handleUserDoesNotExistsException(UserDoesNotExistsExcpetion e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Exception handler for InvalidParametersException.
     *
     * @param e The exception instance.
     * @return ResponseEntity with the exception message and HTTP status code.
     */
    @ExceptionHandler(InvalidParametersException.class)
    public ResponseEntity<String> handleInvalidParametersException(InvalidParametersException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
