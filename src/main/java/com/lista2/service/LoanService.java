package com.lista2.service;

import com.lista2.Book;
import com.lista2.Loan;
import com.lista2.User;
import com.lista2.exceptions.*;
import com.lista2.repositories.IBookRepository;
import com.lista2.repositories.ILoanRepository;
import com.lista2.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * Service class for managing loans.
 */
@Service
public class LoanService {
    private final ILoanRepository loanRepository;
    private final IUserRepository userRepository;
    private final IBookRepository bookRepository;

    @Autowired
    public LoanService(ILoanRepository loanRepository, IUserRepository userRepository, IBookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Adds a new loan to the repository.
     *
     * @param loan The loan to be added.
     * @return ResponseEntity containing a message indicating the success of the operation.
     * @throws LoanAlreadyExistsException If a loan with the same ID already exists.
     * @throws InvalidParametersException If the loan parameters are invalid.
     * @throws UserDoesNotExistsExcpetion If the user associated with the loan does not exist.
     */
    public ResponseEntity<String> addLoan(Loan loan) {
        Optional<Loan> existingLoan = loanRepository.findById(loan.getId());
        if (existingLoan.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already exists");
        } else {
            Optional<User> userOptional = userRepository.findById(loan.getUserLoan().getId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (loan.getLoanEndDate().before(loan.getLoanStartDate())) {
                    throw new InvalidParametersException("End date cannot be before start date.");
                }
                if (loan.getUserLoan() == null || loan.getBookLoan() == null) {
                    throw new InvalidParametersException("User ID or Book ID cannot be null.");
                }
                loan.setUserLoan(user);
                loanRepository.save(loan);
                return new ResponseEntity<>("Loan added successfully", HttpStatus.OK);
            } else {
                throw new UserDoesNotExistsExcpetion(loan.getUserLoan().getId());
            }
        }
    }

    /**
     * Deletes a loan from the repository.
     *
     * @param loanId The ID of the loan to be deleted.
     * @return ResponseEntity containing a message indicating the success of the operation.
     * @throws LoanDoesntExistsException If the specified loan does not exist.
     */
    public ResponseEntity<String> deleteLoan(Integer loanId) {
        Optional<Loan> existingLoan = loanRepository.findById(loanId);
        if (existingLoan.isEmpty()) {
            throw new LoanDoesntExistsException("Loan not found.");
        } else {
            loanRepository.deleteById(loanId);
            return new ResponseEntity<>("Loan deleted successfully", HttpStatus.OK);
        }
    }

    /**
     * Retrieves all loans from the repository.
     *
     * @return An Iterable containing all loans.
     */
    public List<LoanDTO> getAllLoans() {
        Iterable<Loan> loans = loanRepository.findAll();
        return StreamSupport.stream(loans.spliterator(), false)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private LoanDTO convertToDTO(Loan loan) {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setId(loan.getId());
        loanDTO.setLoanStartDate(loan.getLoanStartDate());
        loanDTO.setLoanEndDate(loan.getLoanEndDate());
        loanDTO.setBookReturnDate(loan.getBookReturnDate());
        loanDTO.setUserLoan(loan.getUserLoan().getId());
        loanDTO.setBookLoan(loan.getBookLoan().getId());

        return loanDTO;
    }

    /**
     * Retrieves loans associated with the currently authenticated user.
     *
     * @return A list of loans associated with the authenticated user.
     * @throws NoLoansException If the authenticated user has no loans.
     */
    public List<Loan> getMyLoans() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String id = (String) authentication.getPrincipal();
            Optional<User> userOptional = userRepository.findById(Integer.valueOf(id));
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                return user.getBookLoanList();
            }
        }
        throw new NoLoansException("You have no loans.");
    }

    /**
     * Updates the return date of a book.
     *
     * @param loanID         The ID of the loan.
     * @param bookReturnDate The new return date of the book.
     * @return ResponseEntity containing a message indicating the success of the operation.
     * @throws InvalidParametersException If the book return date is before the loan start date.
     * @throws LoanDoesntExistsException  If the specified loan does not exist.
     */
    public ResponseEntity<String> updateBookReturnDate(Integer loanID, Date bookReturnDate) {
        Optional<Loan> loanOptional = loanRepository.findById(loanID);
        if (loanOptional.isPresent()) {
            Loan loan = loanOptional.get();
            if (bookReturnDate.before(loan.getLoanStartDate())) {
                throw new InvalidParametersException("Book return date cannot be before loan start date.");
            }
            loan.setBookReturnDate(bookReturnDate);
            loanRepository.save(loan);
            return new ResponseEntity<>("Book return date set as '" + bookReturnDate + "'", HttpStatus.OK);
        } else {
            throw new LoanDoesntExistsException("Loan not found, can't update the date.");
        }
    }

    public LoanDTO convertToDto(Loan loan) {
        LoanDTO loanDto = new LoanDTO();
        loanDto.setId(loan.getId());
        loanDto.setLoanStartDate(loan.getLoanStartDate());
        loanDto.setLoanEndDate(loan.getLoanEndDate());
        loanDto.setBookReturnDate(loan.getBookReturnDate());

        UserDTO userDto = new UserDTO();
        userDto.setId(loan.getUserLoan().getId());
        loanDto.setUserLoan(userDto.getId());

        BookDTO bookDto = new BookDTO();
        bookDto.setId(loan.getBookLoan().getId());
        loanDto.setBookLoan(bookDto.getId());

        return loanDto;
    }

    public Loan convertToEntity(LoanDTO loanDto) {
        Loan loan = new Loan();
        loan.setId(loanDto.getId());
        loan.setLoanStartDate(loanDto.getLoanStartDate());
        loan.setLoanEndDate(loanDto.getLoanEndDate());
        loan.setBookReturnDate(loanDto.getBookReturnDate());

        User user = userRepository.findById(loanDto.getUserLoan())
                .orElseThrow(() -> new UserDoesNotExistsExcpetion(loanDto.getUserLoan()));
        loan.setUserLoan(user);

        Book book = bookRepository.findById(loanDto.getBookLoan())
                .orElseThrow(() -> new InvalidParametersException("Book ID is invalid"));
        loan.setBookLoan(book);

        return loan;
    }



}
