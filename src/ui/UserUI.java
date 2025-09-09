package ui;

import java.util.List;
import java.util.Scanner;

import auth.AuthenticationManager;
import model.User;
import service.UserService;

public class UserUI {
    private Scanner scanner;
    private UserService userService;
    private AuthenticationManager authManager;
    
    public UserUI(Scanner scanner, UserService userService, AuthenticationManager authManager) {
        this.scanner = scanner;
        this.userService = userService;
        this.authManager = authManager;
    }
    
    public void showUserManagementMenu() {
        while (true) {
            clearScreen();
            System.out.println("=== User Management ===");
            System.out.println("1. Add New User");
            System.out.println("2. View All Users");
            System.out.println("3. Update User");
            System.out.println("4. Delete User");
            System.out.println("5. Change Password");
            System.out.println("6. Back to Main Menu");
            
            int choice = getIntInput("Please select an option: ");
            
            switch (choice) {
                case 1: addNewUser(); break;
                case 2: viewAllUsers(); break;
                case 3: updateUser(); break;
                case 4: deleteUser(); break;
                case 5: changeUserPassword(); break;
                case 6: return; // Return to previous menu
                default: 
                    System.out.println("Invalid option. Please try again.");
                    pressEnterToContinue();
            }
        }
    }
    
    // User Management Methods
    private void addNewUser() {
        clearScreen();
        System.out.println("=== Add New User ===");
        
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        
        System.out.println("Role:");
        System.out.println("1. Admin");
        System.out.println("2. Staff");
        int roleChoice = getIntInput("Select role: ");
        
        String role = "staff";
        if (roleChoice == 1) {
            role = "admin";
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // In real implementation, this should be hashed
        user.setRole(role);
        
        boolean success = userService.registerUser(user, authManager.getCurrentUser());
        if (success) {
            System.out.println("User added successfully!");
        } else {
            System.out.println("Failed to add user. Username may already exist.");
        }
        pressEnterToContinue();
    }
    
    private void viewAllUsers() {
        clearScreen();
        System.out.println("=== All Users ===");
        
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.printf("%-5s %-20s %-10s%n", "ID", "Username", "Role");
            System.out.println("----------------------------------------");
            for (User user : users) {
                System.out.printf("%-5d %-20s %-10s%n", 
                    user.getUserId(), 
                    user.getUsername(), 
                    user.getRole());
            }
        }
        pressEnterToContinue();
    }
    
    private void updateUser() {
        clearScreen();
        System.out.println("=== Update User ===");
        
        int userId = getIntInput("Enter User ID to update: ");
        User user = userService.findUserByUsername(String.valueOf(userId)); // This is a simplification
        
        // In a real implementation, we would have a method to find user by ID
        // For now, we'll skip this implementation
        System.out.println("User update functionality would be implemented here.");
        pressEnterToContinue();
    }
    
    private void deleteUser() {
        clearScreen();
        System.out.println("=== Delete User ===");
        
        int userId = getIntInput("Enter User ID to delete: ");
        
        // Check if trying to delete self
        if (userId == authManager.getCurrentUser().getUserId()) {
            System.out.println("You cannot delete your own account.");
            pressEnterToContinue();
            return;
        }
        
        boolean success = userService.deleteUser(userId, authManager.getCurrentUser());
        if (success) {
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("Failed to delete user.");
        }
        pressEnterToContinue();
    }
    
    private void changeUserPassword() {
        clearScreen();
        System.out.println("=== Change User Password ===");
        
        int userId = getIntInput("Enter User ID to change password: ");
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine().trim();
        
        boolean success = userService.changePassword(userId, newPassword, authManager.getCurrentUser());
        if (success) {
            System.out.println("Password changed successfully!");
        } else {
            System.out.println("Failed to change password.");
        }
        pressEnterToContinue();
    }
    
    // Utility methods
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    
    private void pressEnterToContinue() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Fallback to empty lines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}