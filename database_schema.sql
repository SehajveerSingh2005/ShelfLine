-- Database Schema for Inventory Management System
-- Note: Database connection parameters can be configured using environment variables:
-- DB_URL, DB_USERNAME, DB_PASSWORD
-- See .env.example for details

-- Users table to store system users
-- Contains user credentials and roles for access control
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'staff') NOT NULL
);

-- Products table to store inventory items
-- Contains product information including name, description, category, price, and quantity
CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    category VARCHAR(50),
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL DEFAULT 0
);

-- Transactions table to log all system activities
-- Tracks user actions, what was modified, and when it happened
CREATE TABLE transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    action VARCHAR(20) NOT NULL,
    table_name VARCHAR(50) NOT NULL,
    record_id INT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    details TEXT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);