package com.lista2.controllers;

import com.lista2.Loan;
import com.lista2.exceptions.*;
import com.lista2.service.LoanDTO;
import com.lista2.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * Controller class for managing loans.
 */
@RestController
@RequestMapping("/loan")
public class LoanController {
    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    /**
     * Adds a new loan.
     *
     * @param loanDTO The loan to add.
     * @return ResponseEntity indicating the result of the operation.
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ResponseEntity<String> addLoan(@RequestBody LoanDTO loanDTO) {
        Loan loan = loanService.convertToEntity(loanDTO);
        return loanService.addLoan(loan);
    }

    /**
     * Deletes a loan by its ID.
     *
     * @param loanId The ID of the loan to delete.
     * @return ResponseEntity indicating the result of the operation.
     */
    @DeleteMapping("/delete/{loanId}")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ResponseEntity<String> deleteLoan(@PathVariable Integer loanId) {
        return loanService.deleteLoan(loanId);
    }

    /**
     * Retrieves all loans.
     *
     * @return Iterable containing all loans.
     */
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public @ResponseBody List<LoanDTO> getAllLoans() {
        return loanService.getAllLoans();
    }

    /**
     * Retrieves loans associated with the current user.
     *
     * @return List containing the loans of the current user.
     */
    @GetMapping("/getMyLoans")
    @PreAuthorize("hasAnyRole('ROLE_READER')")
    public @ResponseBody List<LoanDTO> getMyLoans() {
        return loanService.getMyLoans();
    }
    
    /**
     * Updates the return date of a book for a loan.
     *
     * @param loanID The ID of the loan.
     * @param date   The new return date.
     * @return ResponseEntity indicating the result of the operation.
     */
    @PostMapping("/returnDate/{loanID}/{date}")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ResponseEntity<String> updateBookReturnDate(@PathVariable Integer loanID, @PathVariable Date date) {
        return loanService.updateBookReturnDate(loanID, date);
    }

    /**
     * Exception handler for NoLoansException.
     *
     * @param e The exception instance.
     * @return ResponseEntity with the exception message and HTTP status code.
     */
    @ExceptionHandler(NoLoansException.class)
    public ResponseEntity<String> handleNoLoansException(NoLoansException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Exception handler for LoanDoesntExistsException.
     *
     * @param e The exception instance.
     * @return ResponseEntity with the exception message and HTTP status code.
     */
    @ExceptionHandler(LoanDoesntExistsException.class)
    public ResponseEntity<String> handleLoanDoesntExistsException(LoanDoesntExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Exception handler for LoanAlreadyExistsException.
     *
     * @param e The exception instance.
     * @return ResponseEntity with the exception message and HTTP status code.
     */
    @ExceptionHandler(LoanAlreadyExistsException.class)
    public ResponseEntity<String> handleLoanAlreadyExistsException(LoanAlreadyExistsException e) {
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
