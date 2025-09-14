# ShelfLine Inventory Management System - Technical Summary

## Overview

This document provides a comprehensive technical summary of the ShelfLine Inventory Management System, detailing its architecture, components, and implementation approach. The system is a console-based application built with Java and MySQL that demonstrates core software engineering principles including Object-Oriented Programming, layered architecture, and proper separation of concerns.

## System Architecture

The application follows a traditional layered architecture pattern with clear separation of concerns:

1. **Presentation Layer** (`cli` package): Handles user interaction through console menus
2. **Service Layer** (`service` package): Implements business logic and transaction management
3. **Data Access Layer** (`dao` package): Provides database access through Data Access Objects
4. **Model Layer** (`model` package): Represents domain entities
5. **Utility Layer** (`util` package): Provides common utilities

## Core Components

### 1. Entity Classes (Model Layer)

#### Product
Represents inventory items with properties for ID, name, quantity, price, and category.

#### User
Represents system users with properties for ID, username, password, and role.

### 2. Data Access Objects (DAO Layer)

All DAOs extend a `BaseDAO` class that provides common database connection functionality.

#### ProductDAO
Handles all database operations related to products:
- Create, read, update, delete products
- Search products by name or category
- Find products with low stock
- Retrieve all distinct categories

#### UserDAO
Handles all database operations related to users:
- User authentication
- Create, read, update, delete users
- Find users by username or ID

### 3. Service Classes (Business Logic Layer)

#### ProductService
Implements product management business logic:
- Product creation with validation
- Product search and retrieval
- Product update and deletion
- Get all distinct categories

#### UserService
Implements user management business logic:
- User authentication
- User creation with validation
- User update and deletion

### 4. Command Line Interface

#### ShelfLineApp
Main application class that:
- Initializes the application components
- Sets up dependency injection
- Starts the main menu loop
- Handles graceful shutdown
- Includes placeholder for Spring IoC container initialization

#### MenuService
Provides a comprehensive console-based interface:
- Displays categorized main menu options
- Handles user input and validation
- Coordinates with ProductService and UserService for operations
- Formats and displays results to the user
- Handles errors and exceptions
- Implements "Press Enter to Continue" functionality
- Provides category selection instead of typing for category-related operations
- Uses Rupee symbol (Rs) for currency display

### 5. Utilities

#### DatabaseConnection
Singleton class for database connection management:
- Centralized connection handling
- Connection pooling simulation
- Proper resource cleanup
- Environment variable support for database configuration (DB_URL, DB_USERNAME, DB_PASSWORD)

## Design Patterns Used

1. **Singleton**: DatabaseConnection for centralized database access
2. **DAO**: Data Access Objects for database operations
3. **Service Layer**: Business logic separated from presentation
4. **Layered Architecture**: Clear separation of concerns

## OOP Principles Applied

1. **Encapsulation**: Private fields with public getter/setter methods
2. **Inheritance**: BaseDAO class for common functionality
3. **Abstraction**: Complex operations hidden behind simple method interfaces

## Error Handling

The system implements comprehensive error handling:
1. **Database Errors**: Connection failures, query errors
2. **Input Errors**: Validation of user inputs
3. **Business Logic Errors**: Constraint violations
4. **System Errors**: Resource management, graceful degradation

## Performance Considerations

1. **Connection Management**: Singleton pattern for database connections
2. **Query Optimization**: Proper indexing strategies
3. **Memory Management**: Proper resource cleanup

## Extensibility

The system is designed for easy extension:
1. **Modular Design**: New features can be added without affecting existing code
2. **Interface-Based**: New implementations can be plugged in
3. **Configuration-Based**: Behavior can be modified through configuration

## Recent Enhancements

1. **Improved User Interface**:
   - Categorized menu system for better organization
   - "Press Enter to Continue" functionality after showing information
   - Category selection instead of typing for category-related operations

2. **Enhanced Currency Display**:
   - Changed currency symbol from dollar ($) to Rupee (₹)

3. **Spring Framework Preparation**:
   - Added placeholder for Spring IoC container initialization
   - Maintained manual dependency injection for backward compatibility

4. **User Management**:
   - Implemented user authentication system
   - Added User entity and UserDAO
   - Integrated UserService for user management

## Future Enhancements

1. **Spring Integration**: Full migration to Spring Framework for IoC and DI
2. **Web Interface**: Migration to web-based UI using Spring Boot
3. **REST API**: Expose functionality through web services
4. **Advanced Reporting**: Charts, graphs, and export capabilities
5. **Notification System**: Email/SMS alerts for low stock
6. **Barcode Integration**: Scanner support for product management