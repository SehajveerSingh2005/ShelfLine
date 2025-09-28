# ShelfLine Inventory Management System - Technical Summary

## Overview

This document provides a comprehensive technical summary of the ShelfLine Inventory Management System, detailing its architecture, components, and implementation approach. The system is a feature-rich application built with Spring Boot, Hibernate, and Spring Data JPA that provides both console-based interaction and REST API endpoints, demonstrating modern software engineering principles including Object-Oriented Programming, layered architecture, and proper separation of concerns.

## System Architecture

The application follows a modern layered architecture pattern with clear separation of concerns:

1. **Presentation Layer** (`cli` package): Handles user interaction through console menus
2. **REST API Layer** (`controller` package): Exposes functionality through REST endpoints
3. **Service Layer** (`service` package): Implements business logic and transaction management
4. **Data Access Layer** (`repository` package): Provides database access through Spring Data JPA repositories
5. **Model Layer** (`model` package): JPA entity classes
6. **Configuration Layer** (`config` package): Spring Boot configuration classes

## Core Components

### 1. Entity Classes (Model Layer)

#### Product
JPA entity representing inventory items with properties for ID, name, quantity, price, and category. Uses JPA annotations for ORM mapping:
- `@Entity` for entity mapping
- `@Table(name = "products")` for table mapping
- `@Id` and `@GeneratedValue` for primary key
- `@Column` for column mapping

#### User
JPA entity representing system users with properties for ID, username, password, and role. Uses JPA annotations for ORM mapping:
- `@Entity` for entity mapping
- `@Table(name = "users")` for table mapping
- `@Id` and `@GeneratedValue` for primary key
- `@Column` for column mapping with constraints

### 2. Spring Data JPA Repositories (Data Access Layer)

#### ProductRepository
Spring Data JPA repository extending `JpaRepository<Product, Long>` with additional query methods:
- Built-in CRUD operations through JpaRepository
- Custom queries using Spring Data method naming conventions
- Custom queries using `@Query` annotations
- Search functionality with `findByNameContainingIgnoreCase`
- Low stock filtering with `findByQuantityLessThanEqual`
- Category operations with `findByCategory`

#### UserRepository
Spring Data JPA repository extending `JpaRepository<User, Long>` with additional query methods:
- Built-in CRUD operations through JpaRepository  
- User authentication with `findByUsernameAndPassword`
- Username lookup with `findByUsername`

### 3. Service Classes (Business Logic Layer)

#### ProductService
Implements product management business logic:
- Product creation with validation
- Product search and retrieval
- Product update and deletion
- Get all distinct categories
- Low stock management
- Category-based operations

#### UserService
Implements user management business logic:
- User authentication
- User creation with validation
- User update and deletion
- Username lookup
- Role-based access control

### 4. Command Line Interface

#### ShelfLineApp
Spring Boot main application class implementing `CommandLineRunner`:
- Initializes the application components as Spring Boot application
- Maintains CLI functionality while running Spring Boot infrastructure
- Handles graceful shutdown with `System.exit(0)`
- Integrates with Spring context for dependency injection
- Allows both CLI and web features to coexist

#### MenuService
Provides a comprehensive console-based interface with role-based access:
- Dynamic menu display based on user role (admin/staff)
- Displays categorized main menu options
- Handles user input and validation
- Coordinates with ProductService and UserService for operations
- Formats and displays results to the user
- Handles errors and exceptions
- Implements "Press Enter to Continue" functionality
- Provides category selection instead of typing for category-related operations
- Uses Rupee symbol (₹) for currency display
- Admin-only user management features

### 5. REST API Controllers

#### ProductController
REST API controller handling product endpoints:
- `@RestController` for REST API functionality
- `@RequestMapping("/api/products")` for base URL mapping
- Full CRUD operations via HTTP methods (GET, POST, PUT, DELETE)
- Error handling with proper HTTP status codes
- Data validation and response formatting

#### UserController
REST API controller handling user endpoints:
- `@RestController` for REST API functionality
- `@RequestMapping("/api/users")` for base URL mapping
- Full CRUD operations and authentication endpoints
- Error handling with proper HTTP status codes
- Data validation and response formatting

### 6. Configuration and Properties

#### Application Properties
Centralized configuration in `application.properties`:
- Database connection details
- JPA/Hibernate configuration
- Server settings
- Logging configuration

## Design Patterns Used

1. **Repository**: Spring Data JPA repositories for data access
2. **Service Layer**: Business logic separated from presentation
3. **Layered Architecture**: Clear separation of concerns
4. **Singleton**: Spring's singleton bean management
5. **Command Line Runner**: Spring Boot CLI integration pattern

## Spring Boot & JPA Features

### 1. Spring Boot Auto-Configuration
- Automatic configuration of common components
- Embedded server configuration (Tomcat on port 8080)
- Database connection auto-configuration
- Spring Data JPA auto-configuration

### 2. Hibernate & JPA Features
- Object-Relational Mapping (ORM)
- Automatic schema generation with `hibernate.ddl-auto=update`
- Transaction management
- Query optimization
- Lazy/eager loading configuration

### 3. Spring Data JPA Benefits
- No need to implement DAO classes with boilerplate code
- Automatic repository implementation
- Built-in methods for common operations
- Custom queries with method naming conventions
- Type-safe query methods

## New Functionality Added

### 1. Enhanced CLI with User Management
- Role-based menu options (admin vs staff)
- Admin-only user management features:
  - View all users
  - Add new users
  - Update user information
  - Delete users (except own account)
- Preventing users from deleting their own accounts
- Dynamic menu based on user role

### 2. Complete REST API
- Full CRUD endpoints for both products and users
- Proper HTTP status codes (200, 201, 204, 400, 401, 404)
- JSON request/response handling
- Path variables and request parameters support
- Consistent error handling

### 3. Modern Architecture Benefits
- Reduced boilerplate code through Spring Data JPA
- Better maintainability and testability
- Built-in security considerations
- Scalable architecture for future enhancements

## OOP Principles Applied

1. **Encapsulation**: Private fields with public getter/setter methods
2. **Abstraction**: Complex operations hidden behind simple method interfaces
3. **Inheritance**: Repository interfaces extending JpaRepository

## Error Handling

The system implements comprehensive error handling:
1. **Database Errors**: Connection failures, query errors (handled by Spring Boot)
2. **Input Errors**: Validation of user inputs in services
3. **Business Logic Errors**: Constraint violations and validation
4. **System Errors**: Resource management, graceful degradation
5. **API Errors**: Proper HTTP status codes for REST endpoints

## Performance Considerations

1. **JPA Optimization**: Hibernate with efficient query execution
2. **Connection Management**: HikariCP connection pool
3. **Caching**: Spring Boot's caching capabilities (ready for implementation)
4. **Query Optimization**: Efficient queries through Spring Data JPA

## Extensibility

The system is designed for easy extension:
1. **Modular Design**: New features can be added without affecting existing code
2. **Spring Framework**: Easy integration of new Spring Boot starters
3. **Interface-Based**: Repository interfaces for new entity types
4. **Configuration-Based**: Behavior can be modified through configuration

## Migration Benefits

### 1. From Traditional DAO to Spring Data JPA
- Eliminated boilerplate DAO code
- Automatic repository implementation
- Type-safe operations
- Reduced potential for errors
- Easier maintenance

### 2. From Spring Core to Spring Boot
- Auto-configuration of common components
- Embedded server functionality
- Simplified deployment
- Built-in monitoring endpoints
- Easier development with Spring Boot DevTools (ready for implementation)

### 3. Addition of REST API
- Web-based integration capabilities
- Mobile application support
- Third-party system integration
- Standard HTTP protocols
- JSON data format

## Current Architecture Summary

The application now combines traditional CLI functionality with modern web services:
- **CLI Interface**: Maintains existing console-based user experience
- **REST API**: Provides programmatic access for integration
- **Database Layer**: JPA/Hibernate ORM for database operations
- **Service Layer**: Business logic with validation
- **Configuration**: Spring Boot auto-configuration
- **Security**: Role-based access control

## Future Enhancement Opportunities

1. **Security**: Add Spring Security for authentication and authorization
2. **Advanced API**: Add pagination, filtering, and advanced search
3. **Caching**: Implement Redis or in-memory caching
4. **Monitoring**: Add Actuator endpoints for monitoring
5. **Testing**: Implement comprehensive unit and integration tests
6. **Microservices**: Potential for service decomposition
7. **Frontend**: Web UI integration with the REST API
8. **Reporting**: Advanced analytics and reporting features