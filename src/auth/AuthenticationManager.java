package auth;

import model.User;
import service.UserService;

public class AuthenticationManager {
    private UserService userService;
    private static User currentUser;
    
    public AuthenticationManager(UserService userService) {
        this.userService = userService;
    }
    
    public boolean login(String username, String password) {
        User user = userService.findUserByUsername(username);
        if (user != null && verifyPassword(password, user.getPassword())) {
            currentUser = user;
            return true;
        }
        return false;
    }
    
    private boolean verifyPassword(String password, String hash) {
        // In a real implementation, this would use a proper password verification method
        // For now, we'll use a simple comparison
        // In practice, you would use PasswordUtils.verifyPassword() method
        return password.equals(hash) || password.hashCode() == hash.hashCode();
    }
    
    public void logout() {
        currentUser = null;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public boolean isAdmin() {
        return isLoggedIn() && "admin".equals(currentUser.getRole());
    }
    
    public boolean isStaff() {
        return isLoggedIn() && "staff".equals(currentUser.getRole());
    }
}