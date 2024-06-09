package com.lista2.service;

import com.lista2.Opinion;
import com.lista2.User;
import com.lista2.exceptions.InvalidParametersException;
import com.lista2.exceptions.OpinionException;
import com.lista2.exceptions.UserDoesNotExistsExcpetion;
import com.lista2.repositories.IOpinionRepository;
import com.lista2.repositories.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.management.openmbean.OpenDataException;
import java.util.Optional;

@Service
public class OpinionService {
    private final IOpinionRepository opinionRepository;
    private final IUserRepository userRepository;

    public OpinionService(IOpinionRepository opinionRepository, IUserRepository userRepository) {
        this.opinionRepository = opinionRepository;
        this.userRepository = userRepository;
    }

    /**
     * Adds a new opinion to the repository.
     * @param opinion The opinion to be added.
     * @return ResponseEntity containing a message indicating the success of the operation.
     * @throws InvalidParametersException If the stars or description of the opinion are null.
     * @throws UserDoesNotExistsExcpetion If the user associated with the opinion does not exist.
     * @throws OpinionException If adding the opinion is not allowed.
     */
    public ResponseEntity<String> addOpinion(Opinion opinion){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String id = (String) authentication.getPrincipal();
            Optional<User> userOptional = userRepository.findById(Integer.valueOf(id));
            if(userOptional.isPresent()){
                if(opinion.getStars() == null || opinion.getDescription() == null){
                    throw new InvalidParametersException("Stars or description cannot be null.");
                }
                else{
                    opinionRepository.save(opinion);
                    return new ResponseEntity<>("Opinion added successfully.", HttpStatus.OK);
                }
            }
            throw new UserDoesNotExistsExcpetion(Integer.valueOf(id));
        }
        throw new OpinionException("Cannot add an opinion.");
    }

    /**
     * Retrieves all opinions from the repository.
     * @return An Iterable containing all opinions.
     */
    public Iterable<Opinion> getAll(){
        return opinionRepository.findAll();
    }
}
