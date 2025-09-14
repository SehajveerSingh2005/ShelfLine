package com.example.shelfline.cli;

import com.example.shelfline.service.ProductService;
import com.example.shelfline.service.UserService;
import com.example.shelfline.dao.ProductDAO;
import com.example.shelfline.dao.UserDAO;
import com.example.shelfline.util.DatabaseConnection;
import com.example.shelfline.model.User;

/**
 * Main application class for the ShelfLine Inventory Management System CLI.
 * This class initializes the application components and starts the main menu loop.
 * 
 * @author ShelfLine Team
 * @version 1.0
 */
public class ShelfLineApp {
    
    private static ProductService productService;
    private static UserService userService;
    private static MenuService menuService;
    
    /**
     * Main method to start the ShelfLine Inventory Management System.
     * Initializes the application components and starts the main menu loop.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("Starting ShelfLine Inventory Management System...");
        
        try {
            // Initialize Spring IoC container (placeholder for future implementation)
            initializeSpringContainer();
            
            // Initialize services
            initializeServices();
            
            // Check if we should exit immediately (for testing)
            boolean exitImmediately = args.length > 0 && "9".equals(args[0]);
            
            // Start the main menu
            menuService.displayMainMenu(exitImmediately);
            
        } catch (Exception e) {
            System.err.println("An error occurred while running the application: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Gracefully shutdown the application
            shutdownApplication();
        }
    }
    
    /**
     * Initializes the Spring IoC container.
     * This is a placeholder method for future Spring integration.
     */
    private static void initializeSpringContainer() {
        // TODO: Implement Spring IoC container initialization
        System.out.println("Initializing Spring IoC container...");
        // For now, we'll use manual dependency injection
    }
    
    /**
     * Initializes the application services and their dependencies.
     */
    private static void initializeServices() {
        try {
            // Initialize database connection
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            
            // Initialize DAOs
            ProductDAO productDAO = new ProductDAO(dbConnection);
            UserDAO userDAO = new UserDAO(dbConnection);
            
            // Initialize services
            productService = new ProductService(productDAO);
            userService = new UserService(userDAO);
            
            // Initialize menu service
            menuService = new MenuService(productService, userService);
            
            System.out.println("Services initialized successfully.");
        } catch (Exception e) {
            System.err.println("Error initializing services: " + e.getMessage());
            throw new RuntimeException("Failed to initialize application services", e);
        }
    }
    
    /**
     * Gracefully shuts down the application.
     * Closes database connections and performs cleanup operations.
     */
    private static void shutdownApplication() {
        System.out.println("\nShutting down ShelfLine Inventory Management System...");
        
        try {
            // Close database connection
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            dbConnection.closeConnection();
            
            System.out.println("Application shutdown complete.");
        } catch (Exception e) {
            System.err.println("Error during application shutdown: " + e.getMessage());
        }
    }
}