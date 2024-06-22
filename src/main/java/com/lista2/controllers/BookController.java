package com.lista2.controllers;

import com.lista2.Book;
import com.lista2.exceptions.BookAlreadyExistsException;
import com.lista2.exceptions.BookDoesNotExistsException;
import com.lista2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * Controller class for managing books.
 */
@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Adds a new book.
     *
     * @param book The book to add.
     * @return ResponseEntity indicating the result of the operation.
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    /**
     * Deletes a book by its ID.
     *
     * @param bookId The ID of the book to delete.
     * @return ResponseEntity indicating the result of the operation.
     */
    @DeleteMapping("/delete/{bookId}")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ResponseEntity<String> deleteBook(@PathVariable Integer bookId) {
        return bookService.deleteBook(bookId);
    }

    /**
     * Retrieves all books.
     *
     * @return Iterable containing all books.
     */
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN','ROLE_READER')")
    //@PreAuthorize("permitAll()")
    public Iterable<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param bookId The ID of the book to retrieve.
     * @return Optional containing the book, or empty if not found.
     */
    @GetMapping("/getBook/{bookId}")
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN','ROLE_READER')")
   // @PreAuthorize("permitAll()")
    public @ResponseBody Optional<Book> getBook(@PathVariable Integer bookId) {
        return bookService.getBook(bookId);
    }

    /**
     * Exception handler for BookDoesNotExistsException.
     *
     * @param e The exception instance.
     * @return ResponseEntity with the exception message and HTTP status code.
     */
    @ExceptionHandler(BookDoesNotExistsException.class)
    public ResponseEntity<String> handleBookDoesNotExistsException(BookDoesNotExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Exception handler for BookAlreadyExistsException.
     *
     * @param e The exception instance.
     * @return ResponseEntity with the exception message and HTTP status code.
     */
    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<String> handleBookAlreadyExistsException(BookAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
