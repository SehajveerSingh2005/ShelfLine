package com.example.shelfline.service;

import com.example.shelfline.dao.UserDAO;
import com.example.shelfline.model.User;
import java.util.List;

/**
 * Service class for managing User entities.
 * Provides business logic for user operations including authentication,
 * CRUD operations, and user management.
 * 
 * This class uses dependency injection for the UserDAO to interact with the database.
 * All operations include appropriate validation to ensure data integrity.
 * 
 * @author ShelfLine Team
 * @version 1.0
 */
public class UserService {
    
    private UserDAO userDAO;
    
    /**
     * Default constructor for UserService.
     * Note: This constructor does not initialize the UserDAO.
     * Use the parameterized constructor for proper dependency injection.
     */
    public UserService() {
    }
    
    /**
     * Constructor for UserService with dependency injection.
     * 
     * @param userDAO the UserDAO to use for database operations
     */
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    /**
     * Sets the UserDAO for this service.
     * This method supports dependency injection.
     * 
     * @param userDAO the UserDAO to use for database operations
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    /**
     * Validates a user's fields according to business rules.
     * 
     * @param user the user to validate
     * @throws IllegalArgumentException if any validation rule is violated
     */
    private void validateUser(User user) throws IllegalArgumentException {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }
        
        if (!user.getRole().equals("admin") && !user.getRole().equals("staff")) {
            throw new IllegalArgumentException("Role must be either 'admin' or 'staff'");
        }
    }
    
    /**
     * Authenticates a user with the provided credentials.
     * 
     * @param username the username to authenticate
     * @param password the password to authenticate
     * @return the authenticated User object if successful, null otherwise
     * @throws IllegalStateException if the UserDAO is not initialized
     */
    public User authenticateUser(String username, String password) throws IllegalStateException {
        if (userDAO == null) {
            throw new IllegalStateException("UserDAO is not initialized");
        }
        
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        
        return userDAO.authenticate(username, password);
    }
    
    /**
     * Adds a new user to the system.
     * 
     * @param user the user to add
     * @return the ID of the newly created user
     * @throws IllegalArgumentException if the user fails validation
     * @throws IllegalStateException if the UserDAO is not initialized
     */
    public long addUser(User user) throws IllegalArgumentException, IllegalStateException {
        if (userDAO == null) {
            throw new IllegalStateException("UserDAO is not initialized");
        }
        
        validateUser(user);
        long id = userDAO.create(user);
        user.setId(id);
        return id;
    }
    
    /**
     * Updates an existing user in the system.
     * 
     * @param user the user to update
     * @return true if the user was updated successfully, false otherwise
     * @throws IllegalArgumentException if the user fails validation
     * @throws IllegalStateException if the UserDAO is not initialized
     */
    public boolean updateUser(User user) throws IllegalArgumentException, IllegalStateException {
        if (userDAO == null) {
            throw new IllegalStateException("UserDAO is not initialized");
        }
        
        validateUser(user);
        return userDAO.update(user);
    }
    
    /**
     * Deletes a user from the system.
     * 
     * @param id the ID of the user to delete
     * @return true if the user was deleted successfully, false otherwise
     * @throws IllegalStateException if the UserDAO is not initialized
     */
    public boolean deleteUser(long id) throws IllegalStateException {
        if (userDAO == null) {
            throw new IllegalStateException("UserDAO is not initialized");
        }
        
        return userDAO.delete(id);
    }
    
    /**
     * Retrieves a user by their ID.
     * 
     * @param id the ID of the user to retrieve
     * @return the User object if found, null otherwise
     * @throws IllegalStateException if the UserDAO is not initialized
     */
    public User getUserById(long id) throws IllegalStateException {
        if (userDAO == null) {
            throw new IllegalStateException("UserDAO is not initialized");
        }
        
        return userDAO.findById(id);
    }
    
    /**
     * Retrieves a user by their username.
     * 
     * @param username the username of the user to retrieve
     * @return the User object if found, null otherwise
     * @throws IllegalStateException if the UserDAO is not initialized
     */
    public User getUserByUsername(String username) throws IllegalStateException {
        if (userDAO == null) {
            throw new IllegalStateException("UserDAO is not initialized");
        }
        
        return userDAO.findByUsername(username);
    }
    
    /**
     * Retrieves all users in the system.
     * 
     * @return a list of all users
     * @throws IllegalStateException if the UserDAO is not initialized
     */
    public List<User> getAllUsers() throws IllegalStateException {
        if (userDAO == null) {
            throw new IllegalStateException("UserDAO is not initialized");
        }
        
        return userDAO.findAll();
    }
}