package com.lista2.service;

import com.lista2.User;
import com.lista2.exceptions.InvalidParametersException;
import com.lista2.exceptions.UserAlreadyExistsException;
import com.lista2.exceptions.UserDoesNotExistsExcpetion;
import com.lista2.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing users.
 */
@Service
public class UserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Adds a new user to the repository.
     *
     * @param user The user to be added.
     * @return ResponseEntity containing a message indicating the success of the operation.
     * @throws UserAlreadyExistsException If a user with the same login already exists.
     * @throws InvalidParametersException If any of the user parameters are null.
     */
    public ResponseEntity<String> addUser(User user) {
        Optional<User> existingUser;
        existingUser = Optional.ofNullable(userRepository.findByLogin(user.getLogin()));
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException(user.getLogin());
        } else {
            if (user.getLogin() == null || user.getPassword() == null || user.getRole() == null
                    || user.geteMail() == null || user.getFullName() == null) {
                throw new InvalidParametersException("None of the user parameters should be null.");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return new ResponseEntity<>("User added successfully", HttpStatus.OK);
        }
    }

    /**
     * Deletes a user from the repository.
     *
     * @param userId The ID of the user to be deleted.
     * @return ResponseEntity containing a message indicating the success of the operation.
     * @throws UserDoesNotExistsExcpetion If the specified user does not exist.
     */
    public ResponseEntity<String> deleteUser(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserDoesNotExistsExcpetion(userId);
        } else {
            userRepository.deleteById(userId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        }

    }

    /**
     * Retrieves all users from the repository.
     *
     * @return An Iterable containing all users.
     */
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public IUserRepository getUserRepository() {
        return userRepository;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
