package dao;

import model.Transaction;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Transaction entities.
 * Provides methods to interact with the transactions table in the database.
 */
public class TransactionDAO extends BaseDAO {
    /**
     * Constructor that accepts a DatabaseConnection parameter
     * @param databaseConnection the database connection to use
     */
    public TransactionDAO(DatabaseConnection databaseConnection) {
        super(databaseConnection);
    }
    
    /**
     * Logs a new transaction in the database
     * @param transaction the transaction to log
     * @return true if the transaction was logged successfully, false otherwise
     */
    public boolean logTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, action, table_name, record_id, timestamp, details) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getUserId());
            stmt.setString(2, transaction.getAction());
            stmt.setString(3, transaction.getTableName());
            stmt.setInt(4, transaction.getRecordId());
            stmt.setTimestamp(5, transaction.getTimestamp());
            stmt.setString(6, transaction.getDetails());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error logging transaction: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Retrieves all transactions from the database
     * @return a list of all transactions
     */
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY timestamp DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getInt("transaction_id"));
                transaction.setUserId(rs.getInt("user_id"));
                transaction.setAction(rs.getString("action"));
                transaction.setTableName(rs.getString("table_name"));
                transaction.setRecordId(rs.getInt("record_id"));
                transaction.setTimestamp(rs.getTimestamp("timestamp"));
                transaction.setDetails(rs.getString("details"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all transactions: " + e.getMessage());
        }
        
        return transactions;
    }
    
    /**
     * Retrieves transactions by user ID
     * @param userId the user ID to filter by
     * @return a list of transactions for the specified user
     */
    public List<Transaction> getTransactionsByUser(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY timestamp DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(rs.getInt("transaction_id"));
                    transaction.setUserId(rs.getInt("user_id"));
                    transaction.setAction(rs.getString("action"));
                    transaction.setTableName(rs.getString("table_name"));
                    transaction.setRecordId(rs.getInt("record_id"));
                    transaction.setTimestamp(rs.getTimestamp("timestamp"));
                    transaction.setDetails(rs.getString("details"));
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving transactions by user: " + e.getMessage());
        }
        
        return transactions;
    }
    
    /**
     * Retrieves transactions by action type
     * @param action the action type to filter by
     * @return a list of transactions with the specified action
     */
    public List<Transaction> getTransactionsByAction(String action) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE action = ? ORDER BY timestamp DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, action);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(rs.getInt("transaction_id"));
                    transaction.setUserId(rs.getInt("user_id"));
                    transaction.setAction(rs.getString("action"));
                    transaction.setTableName(rs.getString("table_name"));
                    transaction.setRecordId(rs.getInt("record_id"));
                    transaction.setTimestamp(rs.getTimestamp("timestamp"));
                    transaction.setDetails(rs.getString("details"));
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving transactions by action: " + e.getMessage());
        }
        
        return transactions;
    }
    
    /**
     * Retrieves transactions within a date range
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a list of transactions within the specified date range
     */
    public List<Transaction> getTransactionsByDateRange(Date startDate, Date endDate) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE timestamp BETWEEN ? AND ? ORDER BY timestamp DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, new Timestamp(startDate.getTime()));
            stmt.setTimestamp(2, new Timestamp(endDate.getTime()));
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(rs.getInt("transaction_id"));
                    transaction.setUserId(rs.getInt("user_id"));
                    transaction.setAction(rs.getString("action"));
                    transaction.setTableName(rs.getString("table_name"));
                    transaction.setRecordId(rs.getInt("record_id"));
                    transaction.setTimestamp(rs.getTimestamp("timestamp"));
                    transaction.setDetails(rs.getString("details"));
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving transactions by date range: " + e.getMessage());
        }
        
        return transactions;
    }
    
    /**
     * Retrieves the most recent transactions up to a specified limit
     * @param limit the maximum number of transactions to retrieve
     * @return a list of recent transactions
     */
    public List<Transaction> getRecentTransactions(int limit) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY timestamp DESC LIMIT ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(rs.getInt("transaction_id"));
                    transaction.setUserId(rs.getInt("user_id"));
                    transaction.setAction(rs.getString("action"));
                    transaction.setTableName(rs.getString("table_name"));
                    transaction.setRecordId(rs.getInt("record_id"));
                    transaction.setTimestamp(rs.getTimestamp("timestamp"));
                    transaction.setDetails(rs.getString("details"));
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving recent transactions: " + e.getMessage());
        }
        
        return transactions;
    }
}