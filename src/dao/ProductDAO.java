package dao;

import model.Product;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

/**
 * Data Access Object for Product entities.
 * Provides methods to interact with the products table in the database.
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
     * @return true if the product was created successfully, false otherwise
     */
    public boolean createProduct(Product product) {
        String sql = "INSERT INTO products (name, description, category, price, quantity) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setString(3, product.getCategory());
            stmt.setBigDecimal(4, product.getPrice());
            stmt.setInt(5, product.getQuantity());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error creating product: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Finds a product by product ID
     * @param productId the product ID to search for
     * @return the Product object if found, null otherwise
     */
    public Product findProductById(int productId) {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("product_id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setCategory(rs.getString("category"));
                    product.setPrice(rs.getBigDecimal("price"));
                    product.setQuantity(rs.getInt("quantity"));
                    return product;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding product by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Retrieves all products from the database
     * @return a list of all products
     */
    public List<Product> findAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setQuantity(rs.getInt("quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all products: " + e.getMessage());
        }
        
        return products;
    }
    
    /**
     * Searches for products by a search term in name, description, or category
     * @param searchTerm the term to search for
     * @return a list of products matching the search term
     */
    public List<Product> searchProducts(String searchTerm) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ? OR description LIKE ? OR category LIKE ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("product_id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setCategory(rs.getString("category"));
                    product.setPrice(rs.getBigDecimal("price"));
                    product.setQuantity(rs.getInt("quantity"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching products: " + e.getMessage());
        }
        
        return products;
    }
    
    /**
     * Updates an existing product in the database
     * @param product the product to update
     * @return true if the product was updated successfully, false otherwise
     */
    public boolean updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, description = ?, category = ?, price = ?, quantity = ? WHERE product_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setString(3, product.getCategory());
            stmt.setBigDecimal(4, product.getPrice());
            stmt.setInt(5, product.getQuantity());
            stmt.setInt(6, product.getProductId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Deletes a product from the database
     * @param productId the ID of the product to delete
     * @return true if the product was deleted successfully, false otherwise
     */
    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM products WHERE product_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Finds products with quantity below a specified threshold
     * @param threshold the quantity threshold
     * @return a list of products with low stock
     */
    public List<Product> findProductsWithLowStock(int threshold) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE quantity < ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, threshold);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("product_id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setCategory(rs.getString("category"));
                    product.setPrice(rs.getBigDecimal("price"));
                    product.setQuantity(rs.getInt("quantity"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding products with low stock: " + e.getMessage());
        }
        
        return products;
    }
}