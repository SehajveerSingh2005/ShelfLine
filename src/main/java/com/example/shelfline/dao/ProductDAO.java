package com.example.shelfline.dao;

import com.example.shelfline.model.Product;
import com.example.shelfline.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Product entities.
 * Provides methods to interact with the products table in the database.
 * Implements CRUD operations and additional search functionality.
 */
public class ProductDAO extends BaseDAO {
    
    /**
     * Constructor that accepts a DatabaseConnection parameter
     * @param databaseConnection the database connection to use
     */
    public ProductDAO(DatabaseConnection databaseConnection) {
        super(databaseConnection);
    }
    
    /**
     * Creates a new product in the database
     * @param product the product to create
     * @return the ID of the newly created product, or -1 if creation failed
     */
    public long create(Product product) {
        String sql = "INSERT INTO products (name, quantity, price, category) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getQuantity());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getCategory());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating product: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
    
    /**
     * Finds a product by ID
     * @param id the ID of the product to find
     * @return the Product object if found, null otherwise
     */
    public Product findById(long id) {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getLong("product_id"));
                    product.setName(rs.getString("name"));
                    product.setQuantity(rs.getInt("quantity"));
                    product.setPrice(rs.getDouble("price"));
                    product.setCategory(rs.getString("category"));
                    return product;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding product by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Retrieves all products from the database
     * @return a list of all products
     */
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("product_id"));
                product.setName(rs.getString("name"));
                product.setQuantity(rs.getInt("quantity"));
                product.setPrice(rs.getDouble("price"));
                product.setCategory(rs.getString("category"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all products: " + e.getMessage());
            e.printStackTrace();
        }
        
        return products;
    }
    
    /**
     * Updates an existing product in the database
     * @param product the product to update
     * @return true if the product was updated successfully, false otherwise
     */
    public boolean update(Product product) {
        String sql = "UPDATE products SET name = ?, quantity = ?, price = ?, category = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getQuantity());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getCategory());
            stmt.setLong(5, product.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Deletes a product from the database
     * @param id the ID of the product to delete
     * @return true if the product was deleted successfully, false otherwise
     */
    public boolean delete(long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Searches for products by category
     * @param category the category to search for
     * @return a list of products in the specified category
     */
    public List<Product> findByCategory(String category) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, category);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getLong("product_id"));
                    product.setName(rs.getString("name"));
                    product.setQuantity(rs.getInt("quantity"));
                    product.setPrice(rs.getDouble("price"));
                    product.setCategory(rs.getString("category"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding products by category: " + e.getMessage());
            e.printStackTrace();
        }
        
        return products;
    }
    
    /**
     * Searches for products by name (partial match)
     * @param name the name pattern to search for
     * @return a list of products matching the name pattern
     */
    public List<Product> findByName(String name) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getLong("product_id"));
                    product.setName(rs.getString("name"));
                    product.setQuantity(rs.getInt("quantity"));
                    product.setPrice(rs.getDouble("price"));
                    product.setCategory(rs.getString("category"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding products by name: " + e.getMessage());
            e.printStackTrace();
        }
        
        return products;
    }
    
    /**
     * Gets products with low stock (quantity less than or equal to the specified threshold)
     * @param threshold the quantity threshold
     * @return a list of products with low stock
     */
    public List<Product> findLowStockProducts(int threshold) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE quantity <= ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, threshold);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getLong("product_id"));
                    product.setName(rs.getString("name"));
                    product.setQuantity(rs.getInt("quantity"));
                    product.setPrice(rs.getDouble("price"));
                    product.setCategory(rs.getString("category"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding low stock products: " + e.getMessage());
            e.printStackTrace();
        }
        
        return products;
    }
}