package com.lista2.controllers;

import com.lista2.LoginForm;
import com.lista2.LoginResponse;
import com.lista2.exceptions.AuthenticationException;
import com.lista2.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for user authentication.
 */
@RestController
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Handles user login request.
     *
     * @param loginForm The login form containing user credentials.
     * @return ResponseEntity with JWT token upon successful login, or an error message if authentication fails.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginForm loginForm) {
        LoginResponse loginResponse = loginService.userLogin(loginForm);
        if (loginResponse == null) {
            throw new AuthenticationException("Wrong login or password.");
        } else {
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        }
    }

    /**
     * Exception handler for AuthenticationException.
     *
     * @param e The exception instance.
     * @return ResponseEntity with the exception message and HTTP status code.
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
