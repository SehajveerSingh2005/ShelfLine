package com.example.shelfline.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnection utility class implementing the Singleton pattern
 * for managing database connections to the inventory_system database.
 */
public class DatabaseConnection {
    // Singleton instance
    private static DatabaseConnection instance;
    
    // Database connection
    private Connection connection;
    
    // Database configuration
    private static final String URL = System.getenv("DB_URL") != null ? System.getenv("DB_URL") : "jdbc:mysql://localhost:3306/inventory_system";
    private static final String USERNAME = System.getenv("DB_USERNAME") != null ? System.getenv("DB_USERNAME") : "root";
    private static final String PASSWORD = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "14june2005";
    
    /**
     * Private constructor to prevent instantiation
     */
    private DatabaseConnection() {
        // Private constructor to enforce singleton pattern
    }
    
    /**
     * Returns the singleton instance of DatabaseConnection
     * @return DatabaseConnection instance
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    /**
     * Returns the database connection, creating a new one if necessary
     * @return Connection to the database
     * @throws SQLException if a database access error occurs
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Create new connection
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC Driver not found", e);
            }
        }
        return connection;
    }
    
    /**
     * Closes the database connection if it exists and is open
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
}