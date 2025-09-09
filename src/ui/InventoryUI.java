package ui;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import auth.AuthenticationManager;
import model.Product;
import service.InventoryService;
import service.ProductService;

public class InventoryUI {
    private Scanner scanner;
    private InventoryService inventoryService;
    private ProductService productService;
    private AuthenticationManager authManager;
    
    public InventoryUI(Scanner scanner, InventoryService inventoryService, ProductService productService, AuthenticationManager authManager) {
        this.scanner = scanner;
        this.inventoryService = inventoryService;
        this.productService = productService;
        this.authManager = authManager;
    }
    
    public void showInventoryTrackingMenu() {
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
    
    // Inventory Tracking Methods
    private void viewLowStockItems() {
        clearScreen();
        System.out.println("=== Low Stock Items ===");
        
        int threshold = getIntInput("Enter low stock threshold: ");
        List<Product> lowStockItems = inventoryService.getLowStockItems(threshold);
        
        if (lowStockItems.isEmpty()) {
            System.out.println("No low stock items found.");
        } else {
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
    
    public void increaseStockQuantity() {
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
    
    public void decreaseStockQuantity() {
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
        
        // Get all products
        List<Product> products = productService.getAllProducts();
        if (products == null || products.isEmpty()) {
            System.out.println("No products found.");
            pressEnterToContinue();
            return;
        }
        
        // Group products by category
        Map<String, List<Product>> productsByCategory = new HashMap<>();
        for (Product product : products) {
            String category = product.getCategory();
            productsByCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(product);
        }
        
        // Display products by category
        for (Map.Entry<String, List<Product>> entry : productsByCategory.entrySet()) {
            String category = entry.getKey();
            List<Product> categoryProducts = entry.getValue();
            
            System.out.println("\n--- " + category + " ---");
            System.out.printf("%-5s %-30s %-10s%n", "ID", "Name", "Price (₹)", "Quantity");
            System.out.println("------------------------------------------------------------");
            
            for (Product product : categoryProducts) {
                System.out.printf("%-5d %-30s ₹%-9.2f %-10d%n",
                    product.getProductId(),
                    product.getName(),
                    product.getPrice(),
                    product.getQuantity());
            }
            
            // Calculate category totals
            int totalQuantity = categoryProducts.stream().mapToInt(Product::getQuantity).sum();
            BigDecimal totalValue = categoryProducts.stream()
                .map(p -> p.getPrice().multiply(new BigDecimal(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            System.out.println("------------------------------------------------------------");
            System.out.printf("Category Total: %d items, Value: ₹%.2f%n", totalQuantity, totalValue);
        }
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