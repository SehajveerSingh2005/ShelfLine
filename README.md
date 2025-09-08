# Inventory Management System

A console-based inventory management application built with Java and MySQL. This system allows administrators and staff to manage product inventory with full CRUD operations, user authentication, and transaction logging.

## Features

- **User Authentication**: Secure login system with role-based access control (Admin/Staff)
- **Product Management**: Full CRUD operations for products (Create, Read, Update, Delete)
- **Inventory Tracking**: Real-time inventory monitoring with low stock alerts
- **Transaction Logging**: Complete audit trail of all user actions
- **Reporting**: Generate various reports on inventory and transactions
- **Dependency Injection**: Custom DI implementation for loose coupling

## System Architecture

The application follows a layered architecture pattern:

1. **Presentation Layer**: Console-based UI with menu navigation
2. **Service Layer**: Business logic implementation
3. **Data Access Layer**: Database operations through DAOs
4. **Model Layer**: Entity classes representing database tables

## Prerequisites

- Java 8 or higher
- MySQL 5.7 or higher
- MySQL Connector/J JDBC Driver (included in lib directory)

## Database Setup

1. Create a MySQL database named `inventory_system`:
   ```sql
   CREATE DATABASE inventory_system;
   ```

2. Execute the database schema script:
   ```bash
   mysql -u root -p inventory_system < database_schema.sql
   ```

3. Create initial admin user:
   ```sql
   INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'admin');
    ```

4. (Optional) Set environment variables for database connection:
   - `DB_URL`: Database connection URL (default: jdbc:mysql://localhost:3306/inventory_system)
   - `DB_USERNAME`: Database username (default: root)
   - `DB_PASSWORD`: Database password (default: password)

## Project Structure

```
InventorySystemCLI/
├── src/
│   ├── model/           # Entity classes
│   ├── dao/             # Data Access Objects
│   ├── service/         # Business logic services
│   ├── util/            # Utility classes
│   ├── auth/            # Authentication classes
│   ├── ui/              # User interface components
│   └── App.java         # Main application class
├── lib/
│   └── mysql-connector-j-9.4.0.jar
├── bin/                 # Compiled classes
└── database_schema.sql # Database schema
```

## Installation

1. Clone or download the repository
2. Set up the MySQL database as described above
3. (Optional) Create a `.env` file based on `.env.example` to configure database connection parameters
4. Compile the Java source files:
   ```bash
   javac -cp "lib/*" -d bin src/**/*.java src/*.java
   ```
4. Run the application:
   ```bash
   java -cp "bin;lib/*" App
   ```

## Usage

1. Start the application
2. Log in with the default admin credentials:
   - Username: `admin`
   - Password: `admin123`
3. Navigate through the menu options to manage inventory

### User Roles

- **Admin**: Full access to all features including user management
- **Staff**: Limited access to view products and update inventory

## Key Components

### Dependency Injection
The application implements a custom dependency injection container through the `ServiceRegistry` class, allowing for loose coupling between components.

### Security
- Password hashing for user authentication
- Role-based access control
- Input validation and sanitization

### Data Access
- DAO pattern for database operations
- PreparedStatement usage to prevent SQL injection
- Connection pooling through singleton pattern

## Development

### Adding New Features

1. Create new entity classes in the `model` package
2. Implement corresponding DAO classes in the `dao` package
3. Add business logic in the `service` package
4. Update the UI in the `ui` package to expose new functionality

### Extending the Menu System

The `MenuSystem` class can be extended by adding new menu methods and integrating them into the existing menu hierarchy.

## Troubleshooting

### Database Connection Issues
- Verify MySQL is running
- Check database credentials in `DatabaseConnection.java`
- Ensure the `inventory_system` database exists
- When using environment variables, ensure they are properly set:
  - `DB_URL`: Database connection URL (default: jdbc:mysql://localhost:3306/inventory_system)
  - `DB_USERNAME`: Database username (default: root)
  - `DB_PASSWORD`: Database password (default: password)

### Compilation Errors
- Ensure all Java files are compiled with the correct classpath
- Verify the MySQL connector JAR is in the lib directory

## Future Enhancements

- Web-based UI using Spring Boot
- REST API for mobile applications
- Advanced reporting with charts and graphs
- Email notifications for low stock alerts
- Barcode scanning integration

## License

This project is for educational purposes and does not have a specific license. Feel free to use and modify the code for learning purposes.
