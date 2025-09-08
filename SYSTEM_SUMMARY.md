# Inventory Management System - Technical Summary

## Overview

This document provides a comprehensive technical summary of the Inventory Management System, detailing its architecture, components, and implementation approach. The system is a console-based application built with Java and MySQL that demonstrates core software engineering principles including Object-Oriented Programming, Dependency Injection, and layered architecture.

## System Architecture

The application follows a traditional layered architecture pattern with clear separation of concerns:

1. **Presentation Layer** (`ui` package): Handles user interaction through console menus
2. **Authentication Layer** (`auth` package): Manages user authentication and session state
3. **Service Layer** (`service` package): Implements business logic and transaction management
4. **Data Access Layer** (`dao` package): Provides database access through Data Access Objects
5. **Model Layer** (`model` package): Represents domain entities
6. **Utility Layer** (`util` package): Provides common utilities and dependency injection

## Core Components

### 1. Entity Classes (Model Layer)

#### User
Represents system users with properties for ID, username, password hash, and role (admin/staff).

#### Product
Represents inventory items with properties for ID, name, description, category, price, and quantity.

#### Transaction
Represents audit log entries with properties for ID, user ID, action type, table name, record ID, timestamp, and details.

### 2. Data Access Objects (DAO Layer)

All DAOs extend a `BaseDAO` class that provides common database connection functionality.

#### UserDAO
Handles all database operations related to users:
- Create, read, update, delete users
- Find users by username or ID
- List all users

#### ProductDAO
Handles all database operations related to products:
- Create, read, update, delete products
- Search products by name or category
- Find products with low stock

#### TransactionDAO
Handles all database operations related to transaction logs:
- Log user actions
- Retrieve transaction history with filtering options

### 3. Service Classes (Business Logic Layer)

#### UserService
Implements user management business logic:
- User authentication with password verification
- User registration with password hashing
- User update and deletion with authorization checks
- Password change functionality

#### ProductService
Implements product management business logic:
- Product creation with validation
- Product search and retrieval
- Product update and deletion with transaction logging

#### InventoryService
Implements inventory-specific operations:
- Increase/decrease product quantities
- Low stock item identification
- Quantity adjustment with validation

#### TransactionService
Implements transaction logging and retrieval:
- Log all user actions with detailed information
- Retrieve transaction history with multiple filter options

### 4. Authentication and Session Management

#### AuthenticationManager
Handles user authentication and session state:
- Login with credential verification
- Logout with session cleanup
- Role-based access checks

### 5. User Interface

#### MenuSystem
Provides a comprehensive console-based interface:
- Role-based menu presentation
- Input validation and error handling
- Data display formatting
- Navigation between different functional areas

### 6. Utilities

#### DatabaseConnection
Singleton class for database connection management:
- Centralized connection handling
- Connection pooling simulation
- Proper resource cleanup
- Environment variable support for database configuration (DB_URL, DB_USERNAME, DB_PASSWORD)

#### PasswordUtils
Provides password security functionality:
- Password hashing with salt
- Password verification

#### ServiceRegistry
Implements dependency injection:
- Service registration and retrieval
- Singleton pattern for container
- Type-safe service access

## Dependency Injection Implementation

The system implements a custom dependency injection container through the `ServiceRegistry` class. This approach provides:

1. **Loose Coupling**: Components depend on abstractions rather than concrete implementations
2. **Testability**: Dependencies can be easily mocked for unit testing
3. **Flexibility**: Service implementations can be swapped without code changes
4. **Centralized Configuration**: All dependencies are managed in one place

The bootstrap process in `App.java` demonstrates manual dependency injection:
1. Services are created in the correct order with their dependencies
2. Services are registered with the `ServiceRegistry`
3. Components retrieve their dependencies from the registry when needed

## Security Features

1. **Password Security**: Passwords are hashed before storage
2. **Role-Based Access Control**: Different menu options based on user roles
3. **Input Validation**: All user inputs are validated and sanitized
4. **SQL Injection Prevention**: Use of PreparedStatement for all database queries

## Database Design

The system uses a normalized relational database schema with three main tables:

1. **users**: Stores user account information
2. **products**: Stores product inventory data
3. **transactions**: Logs all user actions for audit purposes

Foreign key constraints ensure data integrity between related records.

## Design Patterns Used

1. **Singleton**: DatabaseConnection, ServiceRegistry
2. **DAO**: Data Access Objects for database operations
3. **Service Layer**: Business logic separated from presentation
4. **Dependency Injection**: Custom container for service management
5. **Factory**: Implicit in service creation

## OOP Principles Applied

1. **Encapsulation**: Private fields with public getter/setter methods
2. **Inheritance**: BaseDAO class for common functionality
3. **Polymorphism**: Role-based method behavior
4. **Abstraction**: Complex operations hidden behind simple method interfaces

## Error Handling

The system implements comprehensive error handling:
1. **Database Errors**: Connection failures, query errors
2. **Input Errors**: Validation of user inputs
3. **Business Logic Errors**: Constraint violations, authorization failures
4. **System Errors**: Resource management, graceful degradation

## Testing Considerations

The architecture supports testing through:
1. **Dependency Injection**: Easy mocking of dependencies
2. **Separation of Concerns**: Isolated testing of layers
3. **Interface-Based Design**: Mock implementations for testing

## Performance Considerations

1. **Connection Management**: Singleton pattern for database connections
2. **Query Optimization**: Proper indexing strategies
3. **Memory Management**: Proper resource cleanup
4. **Pagination**: For large dataset handling (future enhancement)

## Extensibility

The system is designed for easy extension:
1. **Modular Design**: New features can be added without affecting existing code
2. **Interface-Based**: New implementations can be plugged in
3. **Configuration-Based**: Behavior can be modified through configuration

## Future Enhancements

1. **Web Interface**: Migration to web-based UI using Spring Boot
2. **REST API**: Expose functionality through web services
3. **Advanced Reporting**: Charts, graphs, and export capabilities
4. **Notification System**: Email/SMS alerts for low stock
5. **Barcode Integration**: Scanner support for product management