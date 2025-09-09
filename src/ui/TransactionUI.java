package ui;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import auth.AuthenticationManager;
import model.Transaction;
import service.TransactionService;

public class TransactionUI {
    private Scanner scanner;
    private TransactionService transactionService;
    private AuthenticationManager authManager;
    
    public TransactionUI(Scanner scanner, TransactionService transactionService, AuthenticationManager authManager) {
        this.scanner = scanner;
        this.transactionService = transactionService;
        this.authManager = authManager;
    }
    
    public void showTransactionLogsMenu() {
        while (true) {
            clearScreen();
            System.out.println("=== Transaction Logs ===");
            System.out.println("1. View All Transactions");
            System.out.println("2. View Transactions by User");
            System.out.println("3. View Transactions by Action");
            System.out.println("4. View Transactions by Date Range");
            System.out.println("5. View Recent Transactions");
            System.out.println("6. Back to Main Menu");
            
            int choice = getIntInput("Please select an option: ");
            
            switch (choice) {
                case 1: viewAllTransactions(); break;
                case 2: viewTransactionsByUser(); break;
                case 3: viewTransactionsByAction(); break;
                case 4: viewTransactionsByDateRange(); break;
                case 5: viewRecentTransactions(); break;
                case 6: return; // Return to previous menu
                default: 
                    System.out.println("Invalid option. Please try again.");
                    pressEnterToContinue();
            }
        }
    }
    
    // Staff-specific methods
    public void showUserTransactionHistory() {
        clearScreen();
        System.out.println("=== Your Transaction History ===");
        
        List<Transaction> transactions = transactionService.getUserTransactions(
            authManager.getCurrentUser().getUserId());
        
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for your account.");
        } else {
            displayTransactions(transactions);
        }
        pressEnterToContinue();
    }
    
    // Transaction Log Methods
    private void viewAllTransactions() {
        clearScreen();
        System.out.println("=== All Transactions ===");
        
        List<Transaction> transactions = transactionService.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            displayTransactions(transactions);
        }
        pressEnterToContinue();
    }
    
    private void viewTransactionsByUser() {
        clearScreen();
        System.out.println("=== Transactions by User ===");
        
        int userId = getIntInput("Enter User ID: ");
        List<Transaction> transactions = transactionService.getUserTransactions(userId);
        
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for this user.");
        } else {
            displayTransactions(transactions);
        }
        pressEnterToContinue();
    }
    
    private void viewTransactionsByAction() {
        clearScreen();
        System.out.println("=== Transactions by Action ===");
        
        System.out.println("Available actions: CREATE, READ, UPDATE, DELETE");
        System.out.print("Enter action: ");
        String action = scanner.nextLine().trim().toUpperCase();
        
        List<Transaction> transactions = transactionService.getTransactionsByAction(action);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for this action.");
        } else {
            displayTransactions(transactions);
        }
        pressEnterToContinue();
    }
    
    private void viewTransactionsByDateRange() {
        clearScreen();
        System.out.println("=== Transactions by Date Range ===");
        
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDateStr = scanner.nextLine().trim();
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDateStr = scanner.nextLine().trim();
        
        try {
            LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            
            List<Transaction> transactions = transactionService.getTransactionsByDateRange(
                Date.valueOf(startDate), Date.valueOf(endDate));
            
            if (transactions.isEmpty()) {
                System.out.println("No transactions found in this date range.");
            } else {
                displayTransactions(transactions);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
        }
        pressEnterToContinue();
    }
    
    private void viewRecentTransactions() {
        clearScreen();
        System.out.println("=== Recent Transactions ===");
        
        int limit = getIntInput("Enter number of recent transactions to display: ");
        List<Transaction> transactions = transactionService.getRecentTransactions(limit);
        
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            displayTransactions(transactions);
        }
        pressEnterToContinue();
    }
    
    private void displayTransactions(List<Transaction> transactions) {
        System.out.printf("%-5s %-10s %-10s %-15s %-10s %-20s %-30s%n", 
            "ID", "User ID", "Action", "Table", "Record ID", "Timestamp", "Details");
        System.out.println("--------------------------------------------------------------------------------------------------------");
        for (Transaction transaction : transactions) {
            System.out.printf("%-5d %-10d %-10s %-15s %-10d %-20s %-30s%n", 
                transaction.getTransactionId(),
                transaction.getUserId(),
                transaction.getAction(),
                transaction.getTableName(),
                transaction.getRecordId(),
                transaction.getTimestamp().toString(),
                transaction.getDetails().length() > 30 ? 
                    transaction.getDetails().substring(0, 27) + "..." : 
                    transaction.getDetails());
        }
    }
    
    // Utility methods
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    
    private void pressEnterToContinue() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Fallback to empty lines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}