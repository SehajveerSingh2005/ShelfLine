# ShelfLine Inventory Management System

A console-based inventory management application built with Java and MySQL. This system allows users to manage product inventory with full CRUD operations through a command-line interface.

## Features

- **Product Management**: Full CRUD operations for products (Create, Read, Update, Delete)
- **Inventory Tracking**: Real-time inventory monitoring with low stock alerts
- **Search Functionality**: Find products by name or category
- **User-Friendly CLI**: Intuitive menu-driven interface
- **Data Validation**: Input validation and error handling
- **Database Integration**: MySQL database with proper connection management
- **Spring Core Integration**: Dependency injection and Inversion of Control for better maintainability

## System Architecture

The application follows a traditional layered architecture pattern with clear separation of concerns:

1. **Presentation Layer** (`cli` package): Handles user interaction through console menus
2. **Service Layer** (`service` package): Implements business logic
3. **Data Access Layer** (`dao` package): Provides database access through Data Access Objects
4. **Model Layer** (`model` package): Represents domain entities
5. **Utility Layer** (`util` package): Provides common utilities
6. **Configuration Layer** (`config` package): Spring configuration classes

## Spring Core Integration

This application has been enhanced with Spring Core to provide dependency injection and inversion of control. The key components are:

- **@Component**: Applied to `MenuService` to make it a Spring-managed bean
- **@Service**: Applied to service classes (`ProductService`, `UserService`)
- **@Repository**: Applied to DAO classes (`ProductDAO`, `UserDAO`)
- **@Configuration**: Used in `AppConfig` to define configuration
- **@ComponentScan**: Enables automatic detection of Spring components
- **@Bean**: Used for defining beans that require special instantiation logic
- **@Autowired**: Used for automatic dependency injection in constructors and setters

Spring automatically wires the dependencies between layers, making the code more modular and testable.

## Prerequisites

- Java 8 or higher
- MySQL 5.7 or higher
- Maven for building and running the project

## Database Setup

1. Create a MySQL database named `inventory_system`:
   ```sql
   CREATE DATABASE inventory_system;
   ```

2. Execute the database schema script:
   ```bash
   mysql -u root -p inventory_system < database_schema.sql
   ```

3. (Optional) Load test data:
   ```bash
   mysql -u root -p inventory_system < test_data.sql
   ```

## Environment Configuration

1. Copy the `.env.example` file to `.env`:
   ```bash
   cp .env.example .env
   ```

2. Edit the `.env` file to match your database configuration if needed:
   ```
   DB_URL=jdbc:mysql://localhost:3306/inventory_system
   DB_USERNAME=your_username
   DB_PASSWORD=your_password
   ```

## Project Structure

```
shelfline/
├── src/                    # Source code
│   └── main/
│       └── java/
│           └── com/
│               └── example/
│                   └── shelfline/
│                       ├── cli/        # Command Line Interface
│                       ├── dao/        # Data Access Objects
│                       ├── model/      # Entity classes
│                       ├── service/    # Business logic
│                       └── util/       # Utility classes
|
├── .env.example           # Environment configuration template
├── database_schema.sql    # Database schema
├── test_data.sql          # Sample data
└── pom.xml               # Maven configuration
```

## Building and Running the Application

1. Compile the project:
   ```bash
   mvn compile
   ```

2. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass="com.example.shelfline.cli.ShelfLineApp"
   ```

## CLI Menu Options

1. **Add Product**: Add a new product to the inventory
2. **View Product by ID**: View details of a specific product
3. **View All Products**: Display all products in the inventory
4. **Update Product**: Modify an existing product's information
5. **Delete Product**: Remove a product from the inventory
6. **Search by Category**: Find products by category
7. **Search by Name**: Find products by name (partial match)
8. **View Low Stock Products**: Display products with low stock levels
9. **Exit**: Gracefully shut down the application

## Development

### Adding New Features

1. Create new entity classes in the `model` package
2. Implement corresponding DAO classes in the `dao` package
3. Add business logic in the `service` package
4. Update the CLI in the `cli` package to expose new functionality

### Extending the Menu System

The `MenuService` class can be extended by adding new menu methods and integrating them into the existing menu hierarchy.

## Troubleshooting

### Database Connection Issues

- Ensure MySQL is running
- Verify database credentials in `.env` file
- Check that the `inventory_system` database exists
- Confirm MySQL Connector/J is in the classpath

### Compilation Errors

- Ensure Java 8+ is installed
- Verify Maven is properly configured
- Check that all dependencies are resolved