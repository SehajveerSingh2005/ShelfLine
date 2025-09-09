package ui;

import java.util.Scanner;

import auth.AuthenticationManager;
import model.Product;
import model.Transaction;
import model.User;
import service.InventoryService;
import service.ProductService;
import service.TransactionService;
import service.UserService;

public class MenuSystem {
    private Scanner scanner;
    private AuthenticationManager authManager;
    private ProductService productService;
    private UserService userService;
    private InventoryService inventoryService;
    private TransactionService transactionService;
    
    // UI components
    private ProductUI productUI;
    private UserUI userUI;
    private InventoryUI inventoryUI;
    private ReportUI reportUI;
    private TransactionUI transactionUI;
    
    public MenuSystem(AuthenticationManager authManager, ProductService productService, 
                     UserService userService, InventoryService inventoryService,
                     TransactionService transactionService) {
        this.scanner = new Scanner(System.in);
        this.authManager = authManager;
        this.productService = productService;
        this.userService = userService;
        this.inventoryService = inventoryService;
        this.transactionService = transactionService;
        
        // Initialize UI components
        this.productUI = new ProductUI(scanner, productService, authManager);
        this.userUI = new UserUI(scanner, userService, authManager);
        this.inventoryUI = new InventoryUI(scanner, inventoryService, productService, authManager);
        this.reportUI = new ReportUI(scanner, productService, inventoryService, transactionService, authManager);
        this.transactionUI = new TransactionUI(scanner, transactionService, authManager);
    }
    
    public void start() {
        System.out.println("=== Inventory Management System ===");
        
        while (true) {
            if (!authManager.isLoggedIn()) {
                showLoginMenu();
            } else if (authManager.isAdmin()) {
                showAdminMenu();
            } else if (authManager.isStaff()) {
                showStaffMenu();
            }
        }
    }
    
    private void showLoginMenu() {
        clearScreen();
        System.out.println("=== Login ===");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        
        if (authManager.login(username, password)) {
            System.out.println("Login successful! Welcome, " + username);
            pressEnterToContinue();
        } else {
            System.out.println("Invalid username or password.");
            pressEnterToContinue();
        }
    }
    
    private void showAdminMenu() {
        while (authManager.isLoggedIn() && authManager.isAdmin()) {
            clearScreen();
            System.out.println("=== Inventory Management System (Admin) ===");
            System.out.println("1. Product Management");
            System.out.println("2. User Management");
            System.out.println("3. Inventory Tracking");
            System.out.println("4. Transaction Logs");
            System.out.println("5. Reports");
            System.out.println("6. Logout");
            
            int choice = getIntInput("Please select an option: ");
            
            switch (choice) {
                case 1: productUI.showProductManagementMenu(); break;
                case 2: userUI.showUserManagementMenu(); break;
                case 3: inventoryUI.showInventoryTrackingMenu(); break;
                case 4: transactionUI.showTransactionLogsMenu(); break;
                case 5: reportUI.showReportsMenu(); break;
                case 6: authManager.logout(); 
                        System.out.println("Logged out successfully.");
                        pressEnterToContinue();
                        break;
                default: 
                    System.out.println("Invalid option. Please try again.");
                    pressEnterToContinue();
            }
        }
    }
    
    private void showStaffMenu() {
        while (authManager.isLoggedIn() && authManager.isStaff()) {
            clearScreen();
            System.out.println("=== Inventory Management System (Staff) ===");
            System.out.println("1. View Products");
            System.out.println("2. Update Inventory");
            System.out.println("3. View Transaction History");
            System.out.println("4. Reports");
            System.out.println("5. Logout");
            
            int choice = getIntInput("Please select an option: ");
            
            switch (choice) {
                case 1: productUI.viewAllProducts(); break;
                case 2: showInventoryUpdateMenu(); break;
                case 3: transactionUI.showUserTransactionHistory(); break;
                case 4: reportUI.showReportsMenu(); break;
                case 5: authManager.logout();
                        System.out.println("Logged out successfully.");
                        pressEnterToContinue();
                        break;
                default: 
                    System.out.println("Invalid option. Please try again.");
                    pressEnterToContinue();
            }
        }
    }
    
    // Inventory Update Menu (Staff)
    private void showInventoryUpdateMenu() {
        while (true) {
            clearScreen();
            System.out.println("=== Update Inventory ===");
            System.out.println("1. Increase Stock Quantity");
            System.out.println("2. Decrease Stock Quantity");
            System.out.println("3. Back to Main Menu");
            
            int choice = getIntInput("Please select an option: ");
            
            switch (choice) {
                case 1: inventoryUI.increaseStockQuantity(); break;
                case 2: inventoryUI.decreaseStockQuantity(); break;
                case 3: return; // Return to previous menu
                default: 
                    System.out.println("Invalid option. Please try again.");
                    pressEnterToContinue();
            }
        }
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