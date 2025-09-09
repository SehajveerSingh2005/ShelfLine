package ui;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import auth.AuthenticationManager;
import model.Product;
import service.ProductService;

public class ProductUI {
    private Scanner scanner;
    private ProductService productService;
    private AuthenticationManager authManager;
    
    public ProductUI(Scanner scanner, ProductService productService, AuthenticationManager authManager) {
        this.scanner = scanner;
        this.productService = productService;
        this.authManager = authManager;
    }
    
    public void showProductManagementMenu() {
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
    
    public void viewAllProducts() {
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