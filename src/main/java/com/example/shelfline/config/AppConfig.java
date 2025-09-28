package com.example.shelfline.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Boot Configuration class for the ShelfLine Inventory Management System.
 * This class configures component scanning for the application.
 * 
 * @author ShelfLine Team
 * @version 1.0
 */
@Configuration
@ComponentScan(basePackages = "com.example.shelfline")
public class AppConfig {
    // Configuration class for component scanning
    // Spring Boot will handle the rest of the configuration via auto-configuration
}