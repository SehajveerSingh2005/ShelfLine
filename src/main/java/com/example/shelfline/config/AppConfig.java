package com.example.shelfline.config;

import com.example.shelfline.dao.ProductDAO;
import com.example.shelfline.dao.UserDAO;
import com.example.shelfline.service.ProductService;
import com.example.shelfline.service.UserService;
import com.example.shelfline.cli.MenuService;
import com.example.shelfline.util.DatabaseConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Configuration class for the ShelfLine Inventory Management System.
 * This class configures the beans required for the application.
 * 
 * @author ShelfLine Team
 * @version 1.0
 */
@Configuration
@ComponentScan(basePackages = "com.example.shelfline")
public class AppConfig {
    
    /**
     * Creates and configures the DatabaseConnection bean.
     * 
     * @return DatabaseConnection instance
     */
    @Bean
    public DatabaseConnection databaseConnection() {
        return DatabaseConnection.getInstance();
    }
    
    /**
     * Creates and configures the ProductDAO bean.
     * 
     * @param databaseConnection the database connection to use
     * @return ProductDAO instance
     */
    @Bean
    public ProductDAO productDAO(DatabaseConnection databaseConnection) {
        return new ProductDAO(databaseConnection);
    }
    
    /**
     * Creates and configures the UserDAO bean.
     * 
     * @param databaseConnection the database connection to use
     * @return UserDAO instance
     */
    @Bean
    public UserDAO userDAO(DatabaseConnection databaseConnection) {
        return new UserDAO(databaseConnection);
    }
}