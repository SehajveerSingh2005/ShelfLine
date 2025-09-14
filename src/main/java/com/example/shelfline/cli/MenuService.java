package com.example.shelfline.cli;

import com.example.shelfline.service.ProductService;
import com.example.shelfline.service.UserService;
import com.example.shelfline.model.Product;
import com.example.shelfline.model.User;

import java.util.List;
import java.util.Scanner;

/**
 * Service class for handling CLI menu operations in the ShelfLine Inventory Management System.
 * This class displays menu options, handles user input, and coordinates with ProductService
 * to perform inventory operations.
 * 
 * @author ShelfLine Team
 * @version 1.0
 */
public class MenuService {
    
    private ProductService productService;
    private UserService userService;
    private Scanner scanner;
    
    /**
     * Constructor for MenuService.
     * 
     * @param productService the ProductService to use for inventory operations
     * @param userService the UserService to use for user operations
     */
    public MenuService(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Displays the main menu and handles user input for menu selections.
     */
    public void displayMainMenu() {
        displayMainMenu(false);
    }
    
    /**
     * Displays the main menu and handles user input for menu selections.
     *
     * @param exitImmediately if true, exits immediately (for testing purposes)
     */
    public void displayMainMenu(boolean exitImmediately) {
        // First, show login screen
        User currentUser = login();
        if (currentUser == null) {
            System.out.println("Login failed. Exiting application...");
            return;
        }
        
        boolean running = true;
        
        // If exitImmediately is true, just exit without showing menu
        if (exitImmediately) {
            System.out.println("Exiting application...");
            return;
        }
        
        while (running) {
            try {
                System.out.println("\n===== ShelfLine Inventory Management System =====");
                System.out.println("Welcome, " + currentUser.getUsername() + " (" + currentUser.getRole() + ")");
                System.out.println("\n--- Product Management ---");
                System.out.println("1. Add Product");
                System.out.println("2. Update Product");
                System.out.println("3. Delete Product");
                System.out.println("\n--- Product Viewing ---");
                System.out.println("4. View Product by ID");
                System.out.println("5. View All Products");
                System.out.println("6. View Low Stock Products");
                System.out.println("\n--- Product Search ---");
                System.out.println("7. Search by Category");
                System.out.println("8. Search by Name");
                System.out.println("\n--- System ---");
                System.out.println("9. Exit");
                System.out.println("================================================");
                System.out.print("Please select an option (1-9): ");
                
                int choice = getIntInput();
                
                switch (choice) {
                    case 1:
                        addProduct();
                        break;
                    case 2:
                        updateProduct();
                        break;
                    case 3:
                        deleteProduct();
                        break;
                    case 4:
                        viewProductById();
                        break;
                    case 5:
                        viewAllProducts();
                        break;
                    case 6:
                        viewLowStockProducts();
                        break;
                    case 7:
                        searchByCategory();
                        break;
                    case 8:
                        searchByName();
                        break;
                    case 9:
                        running = false;
                        System.out.println("Thank you for using ShelfLine Inventory Management System!");
                        break;
                    default:
                        System.out.println("Invalid option. Please select a number between 1 and 9.");
                }
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
    }
    
    /**
     * Handles adding a new product to the inventory.
     */
    private void addProduct() {
        try {
            System.out.println("\n===== Add New Product =====");
            
            System.out.print("Enter product name: ");
            String name = scanner.nextLine().trim();
            
            System.out.print("Enter product quantity: ");
            int quantity = getIntInput();
            
            System.out.print("Enter product price: ");
            double price = getDoubleInput();
            
            System.out.println("Select product category:");
            String category = selectCategory();
            if (category == null) {
                System.out.print("Enter new product category: ");
                category = scanner.nextLine().trim();
            }
            
            Product product = new Product(name, quantity, price, category);
            long productId = productService.addProduct(product);
            
            System.out.println("Product added successfully with ID: " + productId);
            pressEnterToContinue();
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
            pressEnterToContinue();
        } catch (Exception e) {
            System.err.println("Error adding product: " + e.getMessage());
            pressEnterToContinue();
        }
    }
    
    /**
     * Handles viewing a product by its ID.
     */
    private void viewProductById() {
        try {
            System.out.println("\n===== View Product by ID =====");
            System.out.print("Enter product ID: ");
            long id = getLongInput();
            
            Product product = productService.getProductById(id);
            
            if (product != null) {
                System.out.println("\nProduct Details:");
                System.out.println("ID: " + product.getId());
                System.out.println("Name: " + product.getName());
                System.out.println("Quantity: " + product.getQuantity());
                System.out.println("Price: $" + String.format("%.2f", product.getPrice()));
                System.out.println("Category: " + product.getCategory());
            } else {
                System.out.println("Product with ID " + id + " not found.");
            }
            pressEnterToContinue();
        } catch (Exception e) {
            System.err.println("Error retrieving product: " + e.getMessage());
            pressEnterToContinue();
        }
    }
    
    /**
     * Handles viewing all products in the inventory.
     */
    private void viewAllProducts() {
        try {
            System.out.println("\n===== All Products =====");
            List<Product> products = productService.getAllProducts();
            
            if (products.isEmpty()) {
                System.out.println("No products found in inventory.");
            } else {
                System.out.printf("%-5s %-20s %-10s %-15s%n", "ID", "Name", "Quantity", "Price", "Category");
                System.out.println("----------------------------------------------------------------");
                
                for (Product product : products) {
                    System.out.printf("%-5d %-20s %-10d $%-9.2f %-15s%n",
                            product.getId(),
                            product.getName(),
                            product.getQuantity(),
                            product.getPrice(),
                            product.getCategory());
                }
            }
            pressEnterToContinue();
        } catch (Exception e) {
            System.err.println("Error retrieving products: " + e.getMessage());
            pressEnterToContinue();
        }
    }
    
    /**
     * Handles updating an existing product.
     */
    private void updateProduct() {
        try {
            System.out.println("\n===== Update Product =====");
            System.out.print("Enter product ID to update: ");
            long id = getLongInput();
            
            Product product = productService.getProductById(id);
            
            if (product == null) {
                System.out.println("Product with ID " + id + " not found.");
                pressEnterToContinue();
                return;
            }
            
            System.out.println("Current product details:");
            System.out.println("Name: " + product.getName());
            System.out.println("Quantity: " + product.getQuantity());
            System.out.println("Price: $" + String.format("%.2f", product.getPrice()));
            System.out.println("Category: " + product.getCategory());
            
            System.out.print("Enter new name (or press Enter to keep current): ");
            String nameInput = scanner.nextLine().trim();
            if (!nameInput.isEmpty()) {
                product.setName(nameInput);
            }
            
            System.out.print("Enter new quantity (or press Enter to keep current): ");
            String quantityInput = scanner.nextLine().trim();
            if (!quantityInput.isEmpty()) {
                int quantity = Integer.parseInt(quantityInput);
                product.setQuantity(quantity);
            }
            
            System.out.print("Enter new price (or press Enter to keep current): ");
            String priceInput = scanner.nextLine().trim();
            if (!priceInput.isEmpty()) {
                double price = Double.parseDouble(priceInput);
                product.setPrice(price);
            }
            
            System.out.println("Select new category (or press Enter to keep current):");
            String categoryInput = scanner.nextLine().trim();
            if (!categoryInput.isEmpty()) {
                String category = selectCategory();
                if (category != null) {
                    product.setCategory(category);
                } else {
                    System.out.print("Enter new category: ");
                    category = scanner.nextLine().trim();
                    if (!category.isEmpty()) {
                        product.setCategory(category);
                    }
                }
            }
            
            boolean updated = productService.updateProduct(product);
            
            if (updated) {
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("Failed to update product.");
            }
            pressEnterToContinue();
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + e.getMessage());
            pressEnterToContinue();
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
            pressEnterToContinue();
        } catch (Exception e) {
            System.err.println("Error updating product: " + e.getMessage());
            pressEnterToContinue();
        }
    }
    
    /**
     * Handles deleting a product from the inventory.
     */
    private void deleteProduct() {
        try {
            System.out.println("\n===== Delete Product =====");
            System.out.print("Enter product ID to delete: ");
            long id = getLongInput();
            
            Product product = productService.getProductById(id);
            
            if (product == null) {
                System.out.println("Product with ID " + id + " not found.");
                pressEnterToContinue();
                return;
            }
            
            System.out.println("Product to delete:");
            System.out.println("Name: " + product.getName());
            System.out.println("Quantity: " + product.getQuantity());
            System.out.println("Price: $" + String.format("%.2f", product.getPrice()));
            System.out.println("Category: " + product.getCategory());
            
            System.out.print("Are you sure you want to delete this product? (y/N): ");
            String confirmation = scanner.nextLine().trim();
            
            if (confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")) {
                boolean deleted = productService.deleteProduct(id);
                
                if (deleted) {
                    System.out.println("Product deleted successfully.");
                } else {
                    System.out.println("Failed to delete product.");
                }
            } else {
                System.out.println("Product deletion cancelled.");
            }
            pressEnterToContinue();
        } catch (Exception e) {
            System.err.println("Error deleting product: " + e.getMessage());
            pressEnterToContinue();
        }
    }
    
    /**
     * Handles searching for products by category.
     */
    private void searchByCategory() {
        try {
            System.out.println("\n===== Search by Category =====");
            String category = selectCategory();
            if (category == null) {
                System.out.print("Enter category to search for: ");
                category = scanner.nextLine().trim();
                if (category.isEmpty()) {
                    System.out.println("No category entered.");
                    pressEnterToContinue();
                    return;
                }
            }
            
            List<Product> products = productService.searchByCategory(category);
            
            if (products.isEmpty()) {
                System.out.println("No products found in category: " + category);
            } else {
                System.out.println("\nProducts in category '" + category + "':");
                System.out.printf("%-5s %-20s %-10s %-10s%n", "ID", "Name", "Quantity", "Price");
                System.out.println("------------------------------------------------");
                
                for (Product product : products) {
                    System.out.printf("%-5d %-20s %-10d $%-9.2f%n",
                            product.getId(),
                            product.getName(),
                            product.getQuantity(),
                            product.getPrice());
                }
            }
            pressEnterToContinue();
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
            pressEnterToContinue();
        } catch (Exception e) {
            System.err.println("Error searching by category: " + e.getMessage());
            pressEnterToContinue();
        }
    }
    
    /**
     * Handles searching for products by name.
     */
    private void searchByName() {
        try {
            System.out.println("\n===== Search by Name =====");
            System.out.print("Enter product name to search for: ");
            String name = scanner.nextLine().trim();
            
            List<Product> products = productService.searchByName(name);
            
            if (products.isEmpty()) {
                System.out.println("No products found with name containing: " + name);
            } else {
                System.out.println("\nProducts with name containing '" + name + "':");
                System.out.printf("%-5s %-20s %-10s %-15s%n", "ID", "Name", "Quantity", "Price", "Category");
                System.out.println("----------------------------------------------------------------");
                
                for (Product product : products) {
                    System.out.printf("%-5d %-20s %-10d $%-9.2f %-15s%n",
                            product.getId(),
                            product.getName(),
                            product.getQuantity(),
                            product.getPrice(),
                            product.getCategory());
                }
            }
            pressEnterToContinue();
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
            pressEnterToContinue();
        } catch (Exception e) {
            System.err.println("Error searching by name: " + e.getMessage());
            pressEnterToContinue();
        }
    }
    
    /**
     * Handles viewing products with low stock.
     */
    private void viewLowStockProducts() {
        try {
            System.out.println("\n===== View Low Stock Products =====");
            System.out.print("Enter stock threshold (products with quantity <= this value): ");
            int threshold = getIntInput();
            
            List<Product> products = productService.getLowStockProducts(threshold);
            
            if (products.isEmpty()) {
                System.out.println("No products found with stock quantity <= " + threshold);
            } else {
                System.out.println("\nLow stock products (quantity <= " + threshold + "):");
                System.out.printf("%-5s %-20s %-10s %-15s%n", "ID", "Name", "Quantity", "Price", "Category");
                System.out.println("----------------------------------------------------------------");
                
                for (Product product : products) {
                    System.out.printf("%-5d %-20s %-10d $%-9.2f %-15s%n",
                            product.getId(),
                            product.getName(),
                            product.getQuantity(),
                            product.getPrice(),
                            product.getCategory());
                }
            }
            pressEnterToContinue();
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
            pressEnterToContinue();
        } catch (Exception e) {
            System.err.println("Error retrieving low stock products: " + e.getMessage());
            pressEnterToContinue();
        }
    }
    
    /**
     * Gets an integer input from the user with validation.
     * 
     * @return the integer value entered by the user
     */
    private int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid integer: ");
            }
        }
    }
    
    /**
     * Gets a long input from the user with validation.
     * 
     * @return the long value entered by the user
     */
    private long getLongInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
    
    /**
     * Gets a double input from the user with validation.
     * 
     * @return the double value entered by the user
     */
    private double getDoubleInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid decimal number: ");
            }
        }
    }
    
    /**
     * Handles user login.
     * 
     * @return the authenticated User object if successful, null otherwise
     */
    private User login() {
        try {
            System.out.println("\n===== ShelfLine Inventory Management System Login =====");
            System.out.print("Username: ");
            String username = scanner.nextLine().trim();
            
            System.out.print("Password: ");
            String password = scanner.nextLine().trim();
            
            User user = userService.authenticateUser(username, password);
            
            if (user != null) {
                System.out.println("Login successful. Welcome, " + user.getUsername() + "!");
                return user;
            } else {
                System.out.println("Invalid username or password.");
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Waits for the user to press Enter to continue.
     */
    private void pressEnterToContinue() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Displays a list of categories and lets the user select one.
     *
     * @return the selected category, or null if no categories exist or user cancels
     */
    private String selectCategory() {
        try {
            List<String> categories = productService.getAllCategories();
            
            if (categories.isEmpty()) {
                System.out.println("No categories found in the database.");
                return null;
            }
            
            System.out.println("\nAvailable Categories:");
            for (int i = 0; i < categories.size(); i++) {
                System.out.println((i + 1) + ". " + categories.get(i));
            }
            System.out.println((categories.size() + 1) + ". Cancel");
            System.out.print("Select a category (1-" + (categories.size() + 1) + "): ");
            
            int choice = getIntInput();
            
            if (choice >= 1 && choice <= categories.size()) {
                return categories.get(choice - 1);
            } else if (choice == categories.size() + 1) {
                return null; // User chose to cancel
            } else {
                System.out.println("Invalid selection.");
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error retrieving categories: " + e.getMessage());
            return null;
        }
    }
}