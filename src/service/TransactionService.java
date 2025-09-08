package service;

import dao.TransactionDAO;
import model.Transaction;
import java.util.List;
import java.sql.Timestamp;
import java.sql.Date;

public class TransactionService {
    private TransactionDAO transactionDAO;

    /**
     * Constructor that accepts TransactionDAO parameter
     * @param transactionDAO the TransactionDAO instance to use
     */
    public TransactionService(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    /**
     * Logs an action in the transaction log
     * @param userId the ID of the user performing the action
     * @param action the action being performed
     * @param tableName the name of the table being affected
     * @param recordId the ID of the record being affected
     * @param details additional details about the action
     * @return true if logging is successful, false otherwise
     */
    public boolean logAction(int userId, String action, String tableName, int recordId, String details) {
        try {
            Transaction transaction = new Transaction();
            transaction.setUserId(userId);
            transaction.setAction(action);
            transaction.setTableName(tableName);
            transaction.setRecordId(recordId);
            transaction.setTimestamp(new Timestamp(System.currentTimeMillis()));
            transaction.setDetails(details);
            
            return transactionDAO.logTransaction(transaction);
        } catch (Exception e) {
            System.err.println("Error logging action: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all transactions
     * @return a list of all transactions
     */
    public List<Transaction> getAllTransactions() {
        try {
            return transactionDAO.getAllTransactions();
        } catch (Exception e) {
            System.err.println("Error retrieving all transactions: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves transactions for a specific user
     * @param userId the user ID to filter by
     * @return a list of transactions for the specified user
     */
    public List<Transaction> getUserTransactions(int userId) {
        try {
            return transactionDAO.getTransactionsByUser(userId);
        } catch (Exception e) {
            System.err.println("Error retrieving user transactions: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves transactions by action type
     * @param action the action type to filter by
     * @return a list of transactions with the specified action
     */
    public List<Transaction> getTransactionsByAction(String action) {
        try {
            return transactionDAO.getTransactionsByAction(action);
        } catch (Exception e) {
            System.err.println("Error retrieving transactions by action: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves transactions within a date range
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a list of transactions within the specified date range
     */
    public List<Transaction> getTransactionsByDateRange(Date startDate, Date endDate) {
        try {
            return transactionDAO.getTransactionsByDateRange(startDate, endDate);
        } catch (Exception e) {
            System.err.println("Error retrieving transactions by date range: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves the most recent transactions up to a specified limit
     * @param limit the maximum number of transactions to retrieve
     * @return a list of recent transactions
     */
    public List<Transaction> getRecentTransactions(int limit) {
        try {
            return transactionDAO.getRecentTransactions(limit);
        } catch (Exception e) {
            System.err.println("Error retrieving recent transactions: " + e.getMessage());
            return null;
        }
    }
}