package com.example.shelfline.service;

import com.example.shelfline.dao.ProductDAO;
import com.example.shelfline.model.Product;
import java.util.List;

/**
 * Service class for managing Product entities.
 * Provides business logic for inventory operations including CRUD operations,
 * search functionality, and stock management.
 * 
 * This class uses dependency injection for the ProductDAO to interact with the database.
 * All operations include appropriate validation to ensure data integrity.
 * 
 * @author ShelfLine Team
 * @version 1.0
 */
public class ProductService {
    
    private ProductDAO productDAO;
    
    /**
     * Default constructor for ProductService.
     * Note: This constructor does not initialize the ProductDAO.
     * Use the parameterized constructor for proper dependency injection.
     */
    public ProductService() {
    }
    
    /**
     * Constructor for ProductService with dependency injection.
     * 
     * @param productDAO the ProductDAO to use for database operations
     */
    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
    
    /**
     * Sets the ProductDAO for this service.
     * This method supports dependency injection.
     * 
     * @param productDAO the ProductDAO to use for database operations
     */
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
    
    /**
     * Validates a product's fields according to business rules.
     * 
     * @param product the product to validate
     * @throws IllegalArgumentException if any validation rule is violated
     */
    private void validateProduct(Product product) throws IllegalArgumentException {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        
        if (product.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        
        if (product.getCategory() == null || product.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
    }
    
    /**
     * Adds a new product to the inventory.
     * 
     * @param product the product to add
     * @return the ID of the newly created product
     * @throws IllegalArgumentException if the product fails validation
     * @throws IllegalStateException if the ProductDAO is not initialized
     */
    public long addProduct(Product product) throws IllegalArgumentException, IllegalStateException {
        if (productDAO == null) {
            throw new IllegalStateException("ProductDAO is not initialized");
        }
        
        validateProduct(product);
        long id = productDAO.create(product);
        product.setId(id);
        return id;
    }
    
    /**
     * Updates an existing product in the inventory.
     * 
     * @param product the product to update
     * @return true if the product was updated successfully, false otherwise
     * @throws IllegalArgumentException if the product fails validation
     * @throws IllegalStateException if the ProductDAO is not initialized
     */
    public boolean updateProduct(Product product) throws IllegalArgumentException, IllegalStateException {
        if (productDAO == null) {
            throw new IllegalStateException("ProductDAO is not initialized");
        }
        
        validateProduct(product);
        return productDAO.update(product);
    }
    
    /**
     * Deletes a product from the inventory.
     * 
     * @param id the ID of the product to delete
     * @return true if the product was deleted successfully, false otherwise
     * @throws IllegalStateException if the ProductDAO is not initialized
     */
    public boolean deleteProduct(long id) throws IllegalStateException {
        if (productDAO == null) {
            throw new IllegalStateException("ProductDAO is not initialized");
        }
        
        return productDAO.delete(id);
    }
    
    /**
     * Retrieves a product by its ID.
     * 
     * @param id the ID of the product to retrieve
     * @return the Product object if found, null otherwise
     * @throws IllegalStateException if the ProductDAO is not initialized
     */
    public Product getProductById(long id) throws IllegalStateException {
        if (productDAO == null) {
            throw new IllegalStateException("ProductDAO is not initialized");
        }
        
        return productDAO.findById(id);
    }
    
    /**
     * Retrieves all products in the inventory.
     * 
     * @return a list of all products
     * @throws IllegalStateException if the ProductDAO is not initialized
     */
    public List<Product> getAllProducts() throws IllegalStateException {
        if (productDAO == null) {
            throw new IllegalStateException("ProductDAO is not initialized");
        }
        
        return productDAO.findAll();
    }
    
    /**
     * Searches for products by category.
     * 
     * @param category the category to search for
     * @return a list of products in the specified category
     * @throws IllegalArgumentException if the category is null or empty
     * @throws IllegalStateException if the ProductDAO is not initialized
     */
    public List<Product> searchByCategory(String category) throws IllegalArgumentException, IllegalStateException {
        if (productDAO == null) {
            throw new IllegalStateException("ProductDAO is not initialized");
        }
        
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        
        return productDAO.findByCategory(category);
    }
    
    /**
     * Searches for products by name (partial match).
     * 
     * @param name the name pattern to search for
     * @return a list of products matching the name pattern
     * @throws IllegalArgumentException if the name is null or empty
     * @throws IllegalStateException if the ProductDAO is not initialized
     */
    public List<Product> searchByName(String name) throws IllegalArgumentException, IllegalStateException {
        if (productDAO == null) {
            throw new IllegalStateException("ProductDAO is not initialized");
        }
        
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        
        return productDAO.findByName(name);
    }
    
    /**
     * Retrieves products with low stock (quantity less than or equal to the specified threshold).
     * 
     * @param threshold the quantity threshold for low stock
     * @return a list of products with low stock
     * @throws IllegalArgumentException if the threshold is negative
     * @throws IllegalStateException if the ProductDAO is not initialized
     */
    public List<Product> getLowStockProducts(int threshold) throws IllegalArgumentException, IllegalStateException {
        if (productDAO == null) {
            throw new IllegalStateException("ProductDAO is not initialized");
        }
        
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold cannot be negative");
        }
        
        return productDAO.findLowStockProducts(threshold);
    }
    
    /**
     * Updates the stock quantity of a product.
     * 
     * @param productId the ID of the product to update
     * @param newQuantity the new quantity for the product
     * @return true if the stock quantity was updated successfully, false otherwise
     * @throws IllegalArgumentException if the new quantity is negative
     * @throws IllegalStateException if the ProductDAO is not initialized
     */
    public boolean updateStockQuantity(long productId, int newQuantity) throws IllegalArgumentException, IllegalStateException {
        if (productDAO == null) {
            throw new IllegalStateException("ProductDAO is not initialized");
        }
        
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        
        Product product = productDAO.findById(productId);
        if (product == null) {
            return false;
        }
        
        product.setQuantity(newQuantity);
        return productDAO.update(product);
    }
    
    /**
     * Retrieves all distinct categories from the products table
     *
     * @return a list of all distinct categories
     * @throws IllegalStateException if the ProductDAO is not initialized
     */
    public List<String> getAllCategories() throws IllegalStateException {
        if (productDAO == null) {
            throw new IllegalStateException("ProductDAO is not initialized");
        }
        
        return productDAO.findAllCategories();
    }
}