package com.example.shelfline.dao;

import com.example.shelfline.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Abstract base class for all DAO classes.
 * Provides common database connection functionality.
 */
public abstract class BaseDAO {
    protected DatabaseConnection databaseConnection;
    protected Connection connection;
    
    /**
     * Constructor that accepts a DatabaseConnection parameter
     * @param databaseConnection the database connection to use
     */
    public BaseDAO(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
        try {
            this.connection = databaseConnection.getConnection();
        } catch (SQLException e) {
            System.err.println("Error getting database connection: " + e.getMessage());
        }
    }
}