package ui;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
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
    
    public MenuSystem(AuthenticationManager authManager, ProductService productService, 
                     UserService userService, InventoryService inventoryService,
                     TransactionService transactionService) {
        this.scanner = new Scanner(System.in);
        this.authManager = authManager;
        this.productService = productService;
        this.userService = userService;
        this.inventoryService = inventoryService;
        this.transactionService = transactionService;
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
                case 1: showProductManagementMenu(); break;
                case 2: showUserManagementMenu(); break;
                case 3: showInventoryTrackingMenu(); break;
                case 4: showTransactionLogsMenu(); break;
                case 5: showReportsMenu(); break;
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
                case 1: viewAllProducts(); break;
                case 2: showInventoryUpdateMenu(); break;
                case 3: showUserTransactionHistory(); break;
                case 4: showReportsMenu(); break;
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
    
    // Product Management Menu (Admin)
    private void showProductManagementMenu() {
        while (true) {
            clearScreen();
            System.out.println("=== Product Management ===");
            System.out.println("1. Add New Product");
            System.out.println("2. View All Products");
            System.out.println("3. Search Products");
            System.out.println("4. Update Product");
            System.out.println("5. Delete Product");
            System.out.println("6. Back to Main Menu");
            
            int choice = getIntInput("Please select an option: ");
            
            switch (choice) {
                case 1: addNewProduct(); break;
                case 2: viewAllProducts(); break;
                case 3: searchProducts(); break;
                case 4: updateProduct(); break;
                case 5: deleteProduct(); break;
                case 6: return; // Return to previous menu
                default: 
                    System.out.println("Invalid option. Please try again.");
                    pressEnterToContinue();
            }
        }
    }
    
    // User Management Menu (Admin)
    private void showUserManagementMenu() {
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
    
    // Inventory Tracking Menu (Both Roles)
    private void showInventoryTrackingMenu() {
        while (true) {
            clearScreen();
            System.out.println("=== Inventory Tracking ===");
            System.out.println("1. View Low Stock Items");
            System.out.println("2. Adjust Stock Quantity");
            System.out.println("3. View by Category");
            System.out.println("4. Inventory Summary");
            System.out.println("5. Back to Main Menu");
            
            int choice = getIntInput("Please select an option: ");
            
            switch (choice) {
                case 1: viewLowStockItems(); break;
                case 2: adjustStockQuantity(); break;
                case 3: viewByCategory(); break;
                case 4: showInventorySummary(); break;
                case 5: return; // Return to previous menu
                default: 
                    System.out.println("Invalid option. Please try again.");
                    pressEnterToContinue();
            }
        }
    }
    
    // Transaction Logs Menu (Admin)
    private void showTransactionLogsMenu() {
        while (true) {
            clearScreen();
            System.out.println("=== Transaction Logs ===");
            System.out.println("1. View All Transactions");
            System.out.println("2. View Transactions by User");
            System.out.println("3. View Transactions by Action");
            System.out.println("4. View Transactions by Date Range");
            System.out.println("5. View Recent Transactions");
            System.out.println("6. Back to Main Menu");
            
            int choice = getIntInput("Please select an option: ");
            
            switch (choice) {
                case 1: viewAllTransactions(); break;
                case 2: viewTransactionsByUser(); break;
                case 3: viewTransactionsByAction(); break;
                case 4: viewTransactionsByDateRange(); break;
                case 5: viewRecentTransactions(); break;
                case 6: return; // Return to previous menu
                default: 
                    System.out.println("Invalid option. Please try again.");
                    pressEnterToContinue();
            }
        }
    }
    
    // Reports Menu (Both Roles)
    private void showReportsMenu() {
        while (true) {
            clearScreen();
            System.out.println("=== Reports ===");
            System.out.println("1. Inventory Summary Report");
            System.out.println("2. Low Stock Report");
            System.out.println("3. Transaction Summary Report");
            System.out.println("4. Back to Main Menu");
            
            int choice = getIntInput("Please select an option: ");
            
            switch (choice) {
                case 1: generateInventorySummaryReport(); break;
                case 2: generateLowStockReport(); break;
                case 3: generateTransactionSummaryReport(); break;
                case 4: return; // Return to previous menu
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
                case 1: increaseStockQuantity(); break;
                case 2: decreaseStockQuantity(); break;
                case 3: return; // Return to previous menu
                default: 
                    System.out.println("Invalid option. Please try again.");
                    pressEnterToContinue();
            }
        }
    }
    
    // Product Management Methods
    private void addNewProduct() {
        clearScreen();
        System.out.println("=== Add New Product ===");
        
        System.out.print("Product Name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Description: ");
        String description = scanner.nextLine().trim();
        
        System.out.print("Category: ");
        String category = scanner.nextLine().trim();
        
        BigDecimal price = getDecimalInput("Price: Rs.");
        int quantity = getIntInput("Quantity: ");
        
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setCategory(category);
        product.setPrice(price);
        product.setQuantity(quantity);
        
        boolean success = productService.addProduct(product, authManager.getCurrentUser());
        if (success) {
            System.out.println("Product added successfully!");
        } else {
            System.out.println("Failed to add product.");
        }
        pressEnterToContinue();
    }
    
    private void viewAllProducts() {
        clearScreen();
        System.out.println("=== All Products ===");
        
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            System.out.printf("%-5s %-30s %-15s %-10s%n", "ID", "Name", "Category", "Price (Rs.)", "Quantity");
            System.out.println("------------------------------------------------------------------------");
            for (Product product : products) {
                System.out.printf("%-5d %-30s %-15s Rs.%-9.2f %-10d%n",
                    product.getProductId(),
                    product.getName(),
                    product.getCategory(),
                    product.getPrice(),
                    product.getQuantity());
            }
        }
        pressEnterToContinue();
    }
    
    private void searchProducts() {
        clearScreen();
        System.out.println("=== Search Products ===");
        
        System.out.print("Enter search term: ");
        String searchTerm = scanner.nextLine().trim();
        
        List<Product> products = productService.searchProducts(searchTerm);
        if (products.isEmpty()) {
            System.out.println("No products found matching your search.");
        } else {
            System.out.println("Search results:");
            System.out.printf("%-5s %-30s %-15s %-10s %-10s%n", "ID", "Name", "Category", "Price (Rs.)", "Quantity");
            System.out.println("------------------------------------------------------------------------");
            for (Product product : products) {
                System.out.printf("%-5d %-30s %-15s Rs.%-9.2f %-10d%n",
                    product.getProductId(),
                    product.getName(),
                    product.getCategory(),
                    product.getPrice(),
                    product.getQuantity());
            }
        }
        pressEnterToContinue();
    }
    
    private void updateProduct() {
        clearScreen();
        System.out.println("=== Update Product ===");
        
        int productId = getIntInput("Enter Product ID to update: ");
        Product product = productService.getProductById(productId);
        
        if (product == null) {
            System.out.println("Product not found.");
            pressEnterToContinue();
            return;
        }
        
        System.out.println("Current product details:");
        System.out.println("Name: " + product.getName());
        System.out.println("Description: " + product.getDescription());
        System.out.println("Category: " + product.getCategory());
        System.out.println("Price: Rs." + product.getPrice());
        System.out.println("Quantity: " + product.getQuantity());
        
        System.out.println("\nEnter new values (press Enter to keep current value):");
        
        System.out.print("Product Name [" + product.getName() + "]: ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            product.setName(name);
        }
        
        System.out.print("Description [" + product.getDescription() + "]: ");
        String description = scanner.nextLine().trim();
        if (!description.isEmpty()) {
            product.setDescription(description);
        }
        
        System.out.print("Category [" + product.getCategory() + "]: ");
        String category = scanner.nextLine().trim();
        if (!category.isEmpty()) {
            product.setCategory(category);
        }
        
        System.out.print("Price [Rs." + product.getPrice() + "]: ");
        String priceStr = scanner.nextLine().trim();
        if (!priceStr.isEmpty()) {
            try {
                BigDecimal price = new BigDecimal(priceStr);
                product.setPrice(price);
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format. Keeping current price.");
            }
        }
        
        System.out.print("Quantity [" + product.getQuantity() + "]: ");
        String quantityStr = scanner.nextLine().trim();
        if (!quantityStr.isEmpty()) {
            try {
                int quantity = Integer.parseInt(quantityStr);
                product.setQuantity(quantity);
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity format. Keeping current quantity.");
            }
        }
        
        boolean success = productService.updateProduct(product, authManager.getCurrentUser());
        if (success) {
            System.out.println("Product updated successfully!");
        } else {
            System.out.println("Failed to update product.");
        }
        pressEnterToContinue();
    }
    
    private void deleteProduct() {
        clearScreen();
        System.out.println("=== Delete Product ===");
        
        int productId = getIntInput("Enter Product ID to delete: ");
        Product product = productService.getProductById(productId);
        
        if (product == null) {
            System.out.println("Product not found.");
            pressEnterToContinue();
            return;
        }
        
        System.out.println("Product details:");
        System.out.println("Name: " + product.getName());
        System.out.println("Category: " + product.getCategory());
        System.out.println("Price: Rs." + product.getPrice());
        System.out.println("Quantity: " + product.getQuantity());
        
        System.out.print("Are you sure you want to delete this product? (y/N): ");
        String confirmation = scanner.nextLine().trim();
        
        if ("y".equalsIgnoreCase(confirmation) || "yes".equalsIgnoreCase(confirmation)) {
            boolean success = productService.deleteProduct(productId, authManager.getCurrentUser());
            if (success) {
                System.out.println("Product deleted successfully!");
            } else {
                System.out.println("Failed to delete product.");
            }
        } else {
            System.out.println("Product deletion cancelled.");
        }
        pressEnterToContinue();
    }
    
    // User Management Methods (Admin)
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
    
    // Inventory Tracking Methods
    private void viewLowStockItems() {
        clearScreen();
        System.out.println("=== Low Stock Items ===");
        
        int threshold = getIntInput("Enter low stock threshold: ");
        List<Product> lowStockItems = inventoryService.getLowStockItems(threshold);
        
        if (lowStockItems.isEmpty()) {
            System.out.println("No low stock items found.");
        } else {
            System.out.printf("%-5s %-30s %-15s %-10s %-10s%n", "ID", "Name", "Category", "Price (Rs.)", "Quantity");
            System.out.println("------------------------------------------------------------------------");
            for (Product product : lowStockItems) {
                System.out.printf("%-5d %-30s %-15s Rs.%-9.2f %-10d%n",
                    product.getProductId(),
                    product.getName(),
                    product.getCategory(),
                    product.getPrice(),
                    product.getQuantity());
            }
        }
        pressEnterToContinue();
    }
    
    private void adjustStockQuantity() {
        clearScreen();
        System.out.println("=== Adjust Stock Quantity ===");
        
        System.out.println("1. Increase Stock");
        System.out.println("2. Decrease Stock");
        int choice = getIntInput("Select option: ");
        
        if (choice == 1) {
            increaseStockQuantity();
        } else if (choice == 2) {
            decreaseStockQuantity();
        } else {
            System.out.println("Invalid option.");
            pressEnterToContinue();
        }
    }
    
    private void increaseStockQuantity() {
        int productId = getIntInput("Enter Product ID: ");
        int amount = getIntInput("Enter quantity to add: ");
        
        boolean success = inventoryService.increaseQuantity(productId, amount, authManager.getCurrentUser());
        if (success) {
            System.out.println("Stock quantity increased successfully!");
        } else {
            System.out.println("Failed to increase stock quantity.");
        }
        pressEnterToContinue();
    }
    
    private void decreaseStockQuantity() {
        int productId = getIntInput("Enter Product ID: ");
        int amount = getIntInput("Enter quantity to remove: ");
        
        boolean success = inventoryService.decreaseQuantity(productId, amount, authManager.getCurrentUser());
        if (success) {
            System.out.println("Stock quantity decreased successfully!");
        } else {
            System.out.println("Failed to decrease stock quantity. Check if sufficient stock is available.");
        }
        pressEnterToContinue();
    }
    
    private void viewByCategory() {
        clearScreen();
        System.out.println("=== View Products by Category ===");
        
        // In a real implementation, we would have a service method to get all categories
        System.out.println("Category filtering would be implemented here.");
        pressEnterToContinue();
    }
    
    private void showInventorySummary() {
        clearScreen();
        System.out.println("=== Inventory Summary ===");
        
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products in inventory.");
        } else {
            int totalProducts = products.size();
            int totalQuantity = products.stream().mapToInt(Product::getQuantity).sum();
            BigDecimal totalValue = products.stream()
                .map(p -> p.getPrice().multiply(new BigDecimal(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            System.out.println("Total Products: " + totalProducts);
            System.out.println("Total Quantity: " + totalQuantity);
            System.out.println("Total Inventory Value: Rs." + totalValue);
        }
        pressEnterToContinue();
    }
    
    // Transaction Log Methods
    private void viewAllTransactions() {
        clearScreen();
        System.out.println("=== All Transactions ===");
        
        List<Transaction> transactions = transactionService.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            displayTransactions(transactions);
        }
        pressEnterToContinue();
    }
    
    private void viewTransactionsByUser() {
        clearScreen();
        System.out.println("=== Transactions by User ===");
        
        int userId = getIntInput("Enter User ID: ");
        List<Transaction> transactions = transactionService.getUserTransactions(userId);
        
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for this user.");
        } else {
            displayTransactions(transactions);
        }
        pressEnterToContinue();
    }
    
    private void viewTransactionsByAction() {
        clearScreen();
        System.out.println("=== Transactions by Action ===");
        
        System.out.println("Available actions: CREATE, READ, UPDATE, DELETE");
        System.out.print("Enter action: ");
        String action = scanner.nextLine().trim().toUpperCase();
        
        List<Transaction> transactions = transactionService.getTransactionsByAction(action);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for this action.");
        } else {
            displayTransactions(transactions);
        }
        pressEnterToContinue();
    }
    
    private void viewTransactionsByDateRange() {
        clearScreen();
        System.out.println("=== Transactions by Date Range ===");
        
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDateStr = scanner.nextLine().trim();
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDateStr = scanner.nextLine().trim();
        
        try {
            LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            
            List<Transaction> transactions = transactionService.getTransactionsByDateRange(
                Date.valueOf(startDate), Date.valueOf(endDate));
            
            if (transactions.isEmpty()) {
                System.out.println("No transactions found in this date range.");
            } else {
                displayTransactions(transactions);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
        }
        pressEnterToContinue();
    }
    
    private void viewRecentTransactions() {
        clearScreen();
        System.out.println("=== Recent Transactions ===");
        
        int limit = getIntInput("Enter number of recent transactions to display: ");
        List<Transaction> transactions = transactionService.getRecentTransactions(limit);
        
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            displayTransactions(transactions);
        }
        pressEnterToContinue();
    }
    
    private void displayTransactions(List<Transaction> transactions) {
        System.out.printf("%-5s %-10s %-10s %-15s %-10s %-20s %-30s%n", 
            "ID", "User ID", "Action", "Table", "Record ID", "Timestamp", "Details");
        System.out.println("--------------------------------------------------------------------------------------------------------");
        for (Transaction transaction : transactions) {
            System.out.printf("%-5d %-10d %-10s %-15s %-10d %-20s %-30s%n", 
                transaction.getTransactionId(),
                transaction.getUserId(),
                transaction.getAction(),
                transaction.getTableName(),
                transaction.getRecordId(),
                transaction.getTimestamp().toString(),
                transaction.getDetails().length() > 30 ? 
                    transaction.getDetails().substring(0, 27) + "..." : 
                    transaction.getDetails());
        }
    }
    
    // Reports Methods
    private void generateInventorySummaryReport() {
        clearScreen();
        System.out.println("=== Inventory Summary Report ===");
        
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products in inventory.");
        } else {
            int totalProducts = products.size();
            int totalQuantity = products.stream().mapToInt(Product::getQuantity).sum();
            BigDecimal totalValue = products.stream()
                .map(p -> p.getPrice().multiply(new BigDecimal(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            System.out.println("INVENTORY SUMMARY REPORT");
            System.out.println("========================");
            System.out.println("Total Products: " + totalProducts);
            System.out.println("Total Quantity: " + totalQuantity);
            System.out.println("Total Inventory Value: Rs." + totalValue);
            System.out.println();
            
            // Category breakdown
            System.out.println("CATEGORY BREAKDOWN:");
            System.out.println("------------------");
            // In a real implementation, we would group by category
            System.out.println("Category breakdown would be implemented here.");
        }
        pressEnterToContinue();
    }
    
    private void generateLowStockReport() {
        clearScreen();
        System.out.println("=== Low Stock Report ===");
        
        int threshold = getIntInput("Enter low stock threshold: ");
        List<Product> lowStockItems = inventoryService.getLowStockItems(threshold);
        
        if (lowStockItems.isEmpty()) {
            System.out.println("No low stock items found.");
        } else {
            System.out.println("LOW STOCK REPORT (Threshold: " + threshold + ")");
            System.out.println("=====================================");
            System.out.printf("%-5s %-30s %-15s %-10s%n", "ID", "Name", "Category", "Price (Rs.)", "Quantity");
            System.out.println("------------------------------------------------------------------------");
            for (Product product : lowStockItems) {
                System.out.printf("%-5d %-30s %-15s Rs.%-9.2f %-10d%n",
                    product.getProductId(),
                    product.getName(),
                    product.getCategory(),
                    product.getPrice(),
                    product.getQuantity());
            }
        }
        pressEnterToContinue();
    }
    
    private void generateTransactionSummaryReport() {
        clearScreen();
        System.out.println("=== Transaction Summary Report ===");
        
        List<Transaction> transactions = transactionService.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("TRANSACTION SUMMARY REPORT");
            System.out.println("==========================");
            System.out.println("Total Transactions: " + transactions.size());
            
            // Action breakdown
            System.out.println("\nACTION BREAKDOWN:");
            System.out.println("-----------------");
            // In a real implementation, we would group by action type
            System.out.println("Action breakdown would be implemented here.");
        }
        pressEnterToContinue();
    }
    
    // Staff-specific methods
    private void showUserTransactionHistory() {
        clearScreen();
        System.out.println("=== Your Transaction History ===");
        
        List<Transaction> transactions = transactionService.getUserTransactions(
            authManager.getCurrentUser().getUserId());
        
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for your account.");
        } else {
            displayTransactions(transactions);
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
    
    private BigDecimal getDecimalInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return new BigDecimal(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid decimal number.");
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