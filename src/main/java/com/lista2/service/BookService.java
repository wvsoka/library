package com.lista2.service;

import com.lista2.Book;
import com.lista2.Opinion;
import com.lista2.exceptions.BookAlreadyExistsException;
import com.lista2.exceptions.BookDoesNotExistsException;
import com.lista2.repositories.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.management.openmbean.OpenDataException;
import java.util.Optional;

/**
 * Service class for managing books.
 */
@Service
public class BookService {
    private final IBookRepository bookRepository;

    @Autowired
    public BookService(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Adds a new book to the repository.
     *
     * @param book The book to be added.
     * @return ResponseEntity containing a message indicating the success of the operation.
     * @throws BookAlreadyExistsException If a book with the same ID already exists.
     */
    public ResponseEntity<String> addBook(Book book) {
        Optional<Book> existingBook = bookRepository.findById(book.getId());
        if (existingBook.isPresent()) {
            throw new BookAlreadyExistsException(book.getId());
        } else {
            bookRepository.save(book);
            return new ResponseEntity<>("Book added successfully", HttpStatus.OK);
        }
    }

    /**
     * Deletes a book from the repository.
     *
     * @param bookId The ID of the book to be deleted.
     * @return ResponseEntity containing a message indicating the success of the operation.
     * @throws BookDoesNotExistsException If the specified book does not exist.
     */
    public ResponseEntity<String> deleteBook(Integer bookId) {
        Optional<Book> existingBook = bookRepository.findById(bookId);
        if (existingBook.isEmpty()) {
            throw new BookDoesNotExistsException("Book not found");
        } else {
            bookRepository.deleteById(bookId);
            return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
        }

    }

    /**
     * Retrieves all books from the repository.
     *
     * @return An Iterable containing all books.
     */
    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    /**
     * Retrieves a book by its ID.
     *
     * @param id The ID of the book to retrieve.
     * @return An Optional containing the book, or empty if not found.
     * @throws BookDoesNotExistsException If the specified book does not exist.
     */
    public Optional<Book> getBook(Integer id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return bookRepository.findById(id);
        } else {
            throw new BookDoesNotExistsException("Book with id '" + id + "' not found.");
        }
    }
}
