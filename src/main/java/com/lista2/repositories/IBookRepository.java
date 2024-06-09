package com.lista2.repositories;

import com.lista2.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing books.
 */
@Repository
public interface IBookRepository extends CrudRepository<Book, Integer> {
}
