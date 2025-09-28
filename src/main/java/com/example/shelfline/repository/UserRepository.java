package com.example.shelfline.repository;

import com.example.shelfline.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for User entities.
 * Provides CRUD operations and additional query methods for users.
 * 
 * @author ShelfLine Team
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Finds a user by username.
     * 
     * @param username the username to search for
     * @return an Optional containing the user if found, empty otherwise
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Finds a user by username and password for authentication.
     * 
     * @param username the username to authenticate
     * @param password the password to authenticate
     * @return an Optional containing the user if authentication is successful, empty otherwise
     */
    Optional<User> findByUsernameAndPassword(String username, String password);
}