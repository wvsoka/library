package com.lista2.repositories;

import com.lista2.Loan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing loans.
 */
@Repository
public interface ILoanRepository extends CrudRepository<Loan, Integer> {
}
