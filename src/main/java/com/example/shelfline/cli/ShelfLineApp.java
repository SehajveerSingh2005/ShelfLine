package com.example.shelfline.cli;

import com.example.shelfline.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Main application class for the ShelfLine Inventory Management System CLI.
 * This class initializes the application components and starts the main menu loop.
 * 
 * @author ShelfLine Team
 * @version 1.0
 */
public class ShelfLineApp {
    
    /**
     * Main method to start the ShelfLine Inventory Management System.
     * Initializes the application components and starts the main menu loop.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("Starting ShelfLine Inventory Management System...");
        
        try {
            // Initialize Spring IoC container
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
            
            // Get the MenuService bean from Spring context
            MenuService menuService = context.getBean(MenuService.class);
            
            System.out.println("Services initialized successfully.");
            
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
     * Gracefully shuts down the application.
     * Closes database connections and performs cleanup operations.
     */
    private static void shutdownApplication() {
        System.out.println("\nShutting down ShelfLine Inventory Management System...");
        
        try {
            System.out.println("Application shutdown complete.");
        } catch (Exception e) {
            System.err.println("Error during application shutdown: " + e.getMessage());
        }
    }
}