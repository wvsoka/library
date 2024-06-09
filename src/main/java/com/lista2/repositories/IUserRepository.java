package com.lista2.repositories;

import com.lista2.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing users.
 */
@Repository
public interface IUserRepository extends CrudRepository<User, Integer> {

    /**
     * Retrieves a user by their login.
     *
     * @param login The login of the user to retrieve.
     * @return The user with the specified login, or null if not found.
     */
    User findByLogin(String login);
}
