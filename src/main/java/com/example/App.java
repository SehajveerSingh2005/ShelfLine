package com.example;

import com.example.shelfline.cli.ShelfLineApp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class.
 * This class serves as the entry point for the Spring Boot application.
 * It implements CommandLineRunner to maintain the CLI functionality.
 */
@SpringBootApplication
public class App implements CommandLineRunner {

    private final ShelfLineApp shelfLineApp;

    public App(ShelfLineApp shelfLineApp) {
        this.shelfLineApp = shelfLineApp;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Run the existing CLI application
        shelfLineApp.run(args);
    }
}