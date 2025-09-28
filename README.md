# ShelfLine Inventory Management System

A comprehensive inventory management application built with Spring Boot, Hibernate, and Spring Data JPA. This system provides both a console-based command-line interface and REST API endpoints for managing product inventory with full CRUD operations.

## Features

- **Product Management**: Full CRUD operations for products (Create, Read, Update, Delete)
- **User Management**: Admin users can manage system users (Create, Read, Update, Delete)
- **Inventory Tracking**: Real-time inventory monitoring with low stock alerts
- **Search Functionality**: Find products by name or category
- **Role-Based Access**: Different menus and functionality based on user role (admin/staff)
- **User-Friendly CLI**: Intuitive menu-driven interface
- **REST API**: Modern API endpoints for programmatic access
- **Data Validation**: Input validation and error handling
- **Database Integration**: MySQL database with JPA/Hibernate ORM
- **Spring Boot Integration**: Auto-configuration and dependency injection for better maintainability

## System Architecture

The application follows a modern layered architecture pattern with clear separation of concerns:

1. **Presentation Layer** (`cli` package): Handles user interaction through console menus
2. **REST API Layer** (`controller` package): Exposes functionality via REST endpoints
3. **Service Layer** (`service` package): Implements business logic
4. **Data Access Layer** (`repository` package): Provides database access through Spring Data JPA repositories
5. **Model Layer** (`model` package): JPA entity classes
6. **Configuration Layer** (`config` package): Spring Boot configuration classes

## Spring Boot & JPA Integration

This application has been migrated from Spring Core to Spring Boot to provide modern development practices. Key enhancements include:

- **Spring Boot**: Auto-configuration, embedded server, simplified setup
- **Spring Data JPA**: Automatic repository implementation with minimal code
- **Hibernate**: Object-relational mapping with advanced features
- **@Entity**: Applied to `User` and `Product` to map to database tables
- **@Component**: Applied to `MenuService` and `ShelfLineApp` to make them Spring-managed beans
- **@Service**: Applied to service classes (`ProductService`, `UserService`)
- **@Repository**: Applied to repository interfaces (`ProductRepository`, `UserRepository`)
- **@RestController**: Applied to API controllers for REST functionality
- **@SpringBootApplication**: Main application annotation for auto-configuration
- **@Autowired**: Used for automatic dependency injection in constructors
- **@CommandLineRunner**: Maintains CLI functionality alongside web features

Spring Boot automatically configures the database connection, JPA, and dependency injection, making the code more modular and testable.

## Prerequisites

- Java 11 or higher
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

Update the `application.properties` file in `src/main/resources/` to match your database configuration if needed:
```
spring.datasource.url=jdbc:mysql://localhost:3306/inventory_system
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```

Alternatively, you can use environment variables:
- `DB_URL` (default: jdbc:mysql://localhost:3306/inventory_system)
- `DB_USERNAME` (default: root)
- `DB_PASSWORD` (default: 14june2005)

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
│                       ├── controller/ # REST API Controllers
│                       ├── model/      # JPA Entity classes
│                       ├── repository/ # Spring Data JPA Repositories
│                       ├── service/    # Business logic
│                       └── config/     # Spring Boot Configuration
│   └── resources/
│       └── application.properties       # Application configuration
├── .env.example           # Environment configuration template
├── database_schema.sql    # Database schema
├── test_data.sql          # Sample data
└── pom.xml               # Maven configuration
```

## Building and Running the Application

1. Compile the project:
   ```bash
   mvn clean compile
   ```

2. Run the application with Spring Boot:
   ```bash
   mvn spring-boot:run
   ```
   
   Or package and run:
   ```bash
   mvn package
   java -jar target/shelfline-1.0-SNAPSHOT.jar
   ```

## CLI Menu Options

### For Admin Users:
1. **Add Product**: Add a new product to the inventory
2. **View Product by ID**: View details of a specific product
3. **View All Products**: Display all products in the inventory
4. **Update Product**: Modify an existing product's information
5. **Delete Product**: Remove a product from the inventory
6. **Search by Category**: Find products by category
7. **Search by Name**: Find products by name (partial match)
8. **View Low Stock Products**: Display products with low stock levels
9. **View All Users (Admin)**: View all system users
10. **Add User (Admin)**: Create new user accounts
11. **Update User (Admin)**: Modify existing user information
12. **Delete User (Admin)**: Remove user accounts (except own account)
13. **Exit**: Gracefully shut down the application

### For Staff Users:
1. **Add Product**: Add a new product to the inventory
2. **View Product by ID**: View details of a specific product
3. **View All Products**: Display all products in the inventory
4. **Update Product**: Modify an existing product's information
5. **Delete Product**: Remove a product from the inventory
6. **Search by Category**: Find products by category
7. **Search by Name**: Find products by name (partial match)
8. **View Low Stock Products**: Display products with low stock levels
9. **Exit**: Gracefully shut down the application

## REST API Endpoints

The application provides comprehensive REST API endpoints:

### Product Endpoints
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product
- `GET /api/products/category/{category}` - Get products by category
- `GET /api/products/search?name={name}` - Search products by name
- `GET /api/products/low-stock?threshold={threshold}` - Get low stock products
- `GET /api/products/categories` - Get all categories

### User Endpoints
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user
- `POST /api/users/login?username={username}&password={password}` - Authenticate user
- `GET /api/users/username/{username}` - Get user by username

## Development

### Adding New Features

1. Create new JPA entity classes in the `model` package
2. Implement corresponding Spring Data JPA repositories in the `repository` package
3. Add business logic in the `service` package
4. Update the CLI in the `cli` package to expose new functionality
5. Add REST API endpoints in the `controller` package

### Extending the Menu System

The `MenuService` class can be extended by adding new menu methods and integrating them into the existing menu hierarchy with role-based access control.

## Troubleshooting

### Database Connection Issues

- Ensure MySQL is running
- Verify database credentials in `application.properties` file
- Check that the `inventory_system` database exists
- Confirm MySQL Connector/J is in the classpath

### Application Startup Issues

- Ensure Java 11+ is installed
- Verify Maven is properly configured
- Check that all dependencies are resolved
- Verify that port 8080 is available (for REST API)

### Compilation Errors

- Run `mvn clean compile` to rebuild the project
- Verify all imports are correct
- Check annotation configurations