package com.example.shelfline.cli;

import com.example.shelfline.service.ProductService;
import com.example.shelfline.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Main application class for the ShelfLine Inventory Management System CLI.
 * This class implements CommandLineRunner to work with Spring Boot.
 * 
 * @author ShelfLine Team
 * @version 1.0
 */
@Component
public class ShelfLineApp implements CommandLineRunner {
    
    private final MenuService menuService;
    private final ApplicationContext applicationContext;

    @Autowired
    public ShelfLineApp(MenuService menuService, ApplicationContext applicationContext) {
        this.menuService = menuService;
        this.applicationContext = applicationContext;
    }
    
    /**
     * Method to run the ShelfLine Inventory Management System.
     * This method is called by Spring Boot application.
     * 
     * @param args command line arguments (not used)
     */
    public void run(String... args) throws Exception {
        System.out.println("Starting ShelfLine Inventory Management System...");
        
        try {
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
    private void shutdownApplication() {
        System.out.println("\nShutting down ShelfLine Inventory Management System...");
        
        try {
            System.out.println("Application shutdown complete.");
            // Exit the JVM to properly terminate the application
            System.exit(0);
        } catch (Exception e) {
            System.err.println("Error during application shutdown: " + e.getMessage());
        }
    }
}