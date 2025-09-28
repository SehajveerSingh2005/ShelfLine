package com.example.shelfline.controller;

import com.example.shelfline.model.User;
import com.example.shelfline.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing User entities.
 * Provides endpoints for CRUD operations and authentication.
 * 
 * @author ShelfLine Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * Retrieves all users in the system.
     * 
     * @return a list of all users
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    /**
     * Retrieves a user by their ID.
     * 
     * @param id the ID of the user to retrieve
     * @return the User object if found, 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Adds a new user to the system.
     * 
     * @param user the user to add
     * @return the newly created User with its ID
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            long id = userService.addUser(user);
            user.setId(id);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Updates an existing user in the system.
     * 
     * @param id the ID of the user to update
     * @param user the updated user information
     * @return 200 if updated successfully, 404 if not found, 400 if invalid input
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            user.setId(id);
            boolean updated = userService.updateUser(user);
            if (updated) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Deletes a user from the system.
     * 
     * @param id the ID of the user to delete
     * @return 204 No Content if deleted successfully, 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Authenticates a user with the provided credentials.
     * 
     * @param username the username to authenticate
     * @param password the password to authenticate
     * @return the authenticated User object if successful, 401 if invalid credentials
     */
    @PostMapping("/login")
    public ResponseEntity<User> authenticateUser(@RequestParam String username, @RequestParam String password) {
        try {
            User user = userService.authenticateUser(username, password);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(401).build(); // Unauthorized
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Retrieves a user by their username.
     * 
     * @param username the username of the user to retrieve
     * @return the User object if found, 404 if not found
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}