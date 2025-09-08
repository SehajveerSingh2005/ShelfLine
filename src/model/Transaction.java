package model;

import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private int userId;
    private String action;
    private String tableName;
    private int recordId;
    private Timestamp timestamp;
    private String details;

    // Default constructor
    public Transaction() {
    }

    // Parameterized constructor
    public Transaction(int transactionId, int userId, String action, String tableName, int recordId, Timestamp timestamp, String details) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.action = action;
        this.tableName = tableName;
        this.recordId = recordId;
        this.timestamp = timestamp;
        this.details = details;
    }

    // Getters
    public int getTransactionId() {
        return transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public String getAction() {
        return action;
    }

    public String getTableName() {
        return tableName;
    }

    public int getRecordId() {
        return recordId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getDetails() {
        return details;
    }

    // Setters
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}