package util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Test class for DatabaseConnection to verify environment variable functionality
 */
public class DatabaseConnectionTest {
    public static void main(String[] args) {
        try {
            // Test the database connection
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            Connection conn = dbConnection.getConnection();
            
            if (conn != null && !conn.isClosed()) {
                System.out.println("Database connection successful!");
                System.out.println("Connected to: " + conn.getMetaData().getURL());
                System.out.println("Connected as user: " + conn.getMetaData().getUserName());
                dbConnection.closeConnection();
            } else {
                System.out.println("Failed to establish database connection.");
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}