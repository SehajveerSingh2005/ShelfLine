package service;

import dao.ProductDAO;
import model.Product;
import model.User;
import java.util.List;

public class ProductService {
    private ProductDAO productDAO;
    private TransactionService transactionService;

    /**
     * Constructor that accepts ProductDAO and TransactionService parameters
     * @param productDAO the ProductDAO instance to use
     * @param transactionService the TransactionService instance to use
     */
    public ProductService(ProductDAO productDAO, TransactionService transactionService) {
        this.productDAO = productDAO;
        this.transactionService = transactionService;
    }

    /**
     * Adds a new product
     * @param product the product to add
     * @param currentUser the user performing the action
     * @return true if addition is successful, false otherwise
     */
    public boolean addProduct(Product product, User currentUser) {
        try {
            // Check if current user has permission to add products
            if (currentUser == null || (!currentUser.isAdmin() && !currentUser.isStaff())) {
                System.err.println("Unauthorized: Only admin and staff users can add products");
                return false;
            }

            boolean result = productDAO.createProduct(product);
            if (result) {
                // Log the addition action
                transactionService.logAction(currentUser.getUserId(), "CREATE", "products", product.getProductId(), 
                    "Product " + product.getName() + " added by " + currentUser.getUsername());
            }
            return result;
        } catch (Exception e) {
            System.err.println("Error adding product: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves a product by its ID
     * @param productId the ID of the product to retrieve
     * @return the Product object if found, null otherwise
     */
    public Product getProductById(int productId) {
        try {
            return productDAO.findProductById(productId);
        } catch (Exception e) {
            System.err.println("Error retrieving product by ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves all products
     * @return a list of all products
     */
    public List<Product> getAllProducts() {
        try {
            return productDAO.findAllProducts();
        } catch (Exception e) {
            System.err.println("Error retrieving all products: " + e.getMessage());
            return null;
        }
    }

    /**
     * Updates an existing product
     * @param product the product to update
     * @param currentUser the user performing the update
     * @return true if update is successful, false otherwise
     */
    public boolean updateProduct(Product product, User currentUser) {
        try {
            // Check if current user has permission to update products
            if (currentUser == null || (!currentUser.isAdmin() && !currentUser.isStaff())) {
                System.err.println("Unauthorized: Only admin and staff users can update products");
                return false;
            }

            boolean result = productDAO.updateProduct(product);
            if (result) {
                // Log the update action
                transactionService.logAction(currentUser.getUserId(), "UPDATE", "products", product.getProductId(), 
                    "Product " + product.getName() + " updated by " + currentUser.getUsername());
            }
            return result;
        } catch (Exception e) {
            System.err.println("Error updating product: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a product
     * @param productId the ID of the product to delete
     * @param currentUser the user performing the deletion
     * @return true if deletion is successful, false otherwise
     */
    public boolean deleteProduct(int productId, User currentUser) {
        try {
            // Check if current user has permission to delete products
            if (currentUser == null || (!currentUser.isAdmin() && !currentUser.isStaff())) {
                System.err.println("Unauthorized: Only admin and staff users can delete products");
                return false;
            }

            boolean result = productDAO.deleteProduct(productId);
            if (result) {
                // Log the deletion action
                transactionService.logAction(currentUser.getUserId(), "DELETE", "products", productId, 
                    "Product ID " + productId + " deleted by " + currentUser.getUsername());
            }
            return result;
        } catch (Exception e) {
            System.err.println("Error deleting product: " + e.getMessage());
            return false;
        }
    }

    /**
     * Searches for products by a search term
     * @param searchTerm the term to search for
     * @return a list of products matching the search term
     */
    public List<Product> searchProducts(String searchTerm) {
        try {
            return productDAO.searchProducts(searchTerm);
        } catch (Exception e) {
            System.err.println("Error searching products: " + e.getMessage());
            return null;
        }
    }
}