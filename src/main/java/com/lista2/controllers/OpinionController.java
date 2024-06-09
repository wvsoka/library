package com.lista2.controllers;

import com.lista2.Opinion;
import com.lista2.exceptions.InvalidParametersException;
import com.lista2.exceptions.OpinionException;
import com.lista2.service.OpinionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing opinions.
 */
@RestController
@RequestMapping("/opinion")
public class OpinionController {
    private final OpinionService opinionService;

    public OpinionController(OpinionService opinionService) {
        this.opinionService = opinionService;
    }

    /**
     * Adds a new opinion.
     *
     * @param opinion The opinion to add.
     * @return ResponseEntity indicating the result of the operation.
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_READER')")
    public ResponseEntity<String> addOpinion(@RequestBody Opinion opinion) {
        return opinionService.addOpinion(opinion);
    }

    /**
     * Retrieves all opinions.
     *
     * @return Iterable containing all opinions.
     */
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN', 'ROLE_READER')")
    public @ResponseBody Iterable<Opinion> getAll() {
        return opinionService.getAll();
    }

    /**
     * Exception handler for OpinionException.
     *
     * @param e The exception instance.
     * @return ResponseEntity with the exception message and HTTP status code.
     */
    @ExceptionHandler(OpinionException.class)
    public ResponseEntity<String> handleOpinionException(OpinionException e) {
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
