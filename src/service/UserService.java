package service;

import dao.UserDAO;
import model.User;
import java.util.List;
import service.TransactionService;

public class UserService {
    private UserDAO userDAO;
    private TransactionService transactionService;

    /**
     * Constructor that accepts UserDAO and TransactionService parameters
     * @param userDAO the UserDAO instance to use
     * @param transactionService the TransactionService instance to use
     */
    public UserService(UserDAO userDAO, TransactionService transactionService) {
        this.userDAO = userDAO;
        this.transactionService = transactionService;
    }

    /**
     * Authenticates a user with the provided username and password
     * @param username the username to authenticate
     * @param password the password to authenticate
     * @return true if authentication is successful, false otherwise
     */
    public boolean authenticateUser(String username, String password) {
        try {
            User user = userDAO.findUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                // Log the authentication action
                transactionService.logAction(user.getUserId(), "LOGIN", "users", user.getUserId(), 
                    "User " + username + " logged in successfully");
                return true;
            }
        } catch (Exception e) {
            System.err.println("Error authenticating user: " + e.getMessage());
        }
        return false;
    }

    /**
     * Finds a user by username
     * @param username the username to search for
     * @return the User object if found, null otherwise
     */
    public User findUserByUsername(String username) {
        try {
            return userDAO.findUserByUsername(username);
        } catch (Exception e) {
            System.err.println("Error finding user by username: " + e.getMessage());
            return null;
        }
    }

    /**
     * Registers a new user
     * @param user the user to register
     * @param currentUser the user performing the registration
     * @return true if registration is successful, false otherwise
     */
    public boolean registerUser(User user, User currentUser) {
        try {
            // Check if current user has permission to register users
            if (currentUser == null || !currentUser.isAdmin()) {
                System.err.println("Unauthorized: Only admin users can register new users");
                return false;
            }

            // Check if username already exists
            if (userDAO.findUserByUsername(user.getUsername()) != null) {
                System.err.println("Username already exists");
                return false;
            }

            boolean result = userDAO.createUser(user);
            if (result) {
                // Log the registration action
                transactionService.logAction(currentUser.getUserId(), "CREATE", "users", user.getUserId(), 
                    "User " + user.getUsername() + " registered by " + currentUser.getUsername());
            }
            return result;
        } catch (Exception e) {
            System.err.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates an existing user
     * @param user the user to update
     * @param currentUser the user performing the update
     * @return true if update is successful, false otherwise
     */
    public boolean updateUser(User user, User currentUser) {
        try {
            // Check if current user has permission to update users
            if (currentUser == null || (!currentUser.isAdmin() && currentUser.getUserId() != user.getUserId())) {
                System.err.println("Unauthorized: Users can only update their own account or admins can update any account");
                return false;
            }

            boolean result = userDAO.updateUser(user);
            if (result) {
                // Log the update action
                transactionService.logAction(currentUser.getUserId(), "UPDATE", "users", user.getUserId(), 
                    "User " + user.getUsername() + " updated by " + currentUser.getUsername());
            }
            return result;
        } catch (Exception e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a user
     * @param userId the ID of the user to delete
     * @param currentUser the user performing the deletion
     * @return true if deletion is successful, false otherwise
     */
    public boolean deleteUser(int userId, User currentUser) {
        try {
            // Check if current user has permission to delete users
            if (currentUser == null || !currentUser.isAdmin()) {
                System.err.println("Unauthorized: Only admin users can delete users");
                return false;
            }

            // Prevent users from deleting themselves
            if (currentUser.getUserId() == userId) {
                System.err.println("Cannot delete your own account");
                return false;
            }

            boolean result = userDAO.deleteUser(userId);
            if (result) {
                // Log the deletion action
                transactionService.logAction(currentUser.getUserId(), "DELETE", "users", userId, 
                    "User ID " + userId + " deleted by " + currentUser.getUsername());
            }
            return result;
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all users
     * @return a list of all users
     */
    public List<User> getAllUsers() {
        try {
            return userDAO.findAllUsers();
        } catch (Exception e) {
            System.err.println("Error retrieving all users: " + e.getMessage());
            return null;
        }
    }

    /**
     * Changes a user's password
     * @param userId the ID of the user whose password to change
     * @param newPassword the new password
     * @param currentUser the user performing the password change
     * @return true if password change is successful, false otherwise
     */
    public boolean changePassword(int userId, String newPassword, User currentUser) {
        try {
            // Check if current user has permission to change password
            if (currentUser == null || (!currentUser.isAdmin() && currentUser.getUserId() != userId)) {
                System.err.println("Unauthorized: Users can only change their own password or admins can change any password");
                return false;
            }

            User user = userDAO.findUserById(userId);
            if (user == null) {
                System.err.println("User not found");
                return false;
            }

            user.setPassword(newPassword);
            boolean result = userDAO.updateUser(user);
            if (result) {
                // Log the password change action
                transactionService.logAction(currentUser.getUserId(), "UPDATE_PASSWORD", "users", userId, 
                    "Password changed for user ID " + userId + " by " + currentUser.getUsername());
            }
            return result;
        } catch (Exception e) {
            System.err.println("Error changing password: " + e.getMessage());
            return false;
        }
    }
}