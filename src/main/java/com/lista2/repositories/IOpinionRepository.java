package com.lista2.repositories;

import com.lista2.Opinion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing opinions.
 */
@Repository
public interface IOpinionRepository extends CrudRepository<Opinion, Integer> {
}
