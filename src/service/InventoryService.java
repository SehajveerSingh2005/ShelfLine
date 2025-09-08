package service;

import dao.ProductDAO;
import model.Product;
import model.User;
import java.util.List;

public class InventoryService {
    private ProductDAO productDAO;
    private TransactionService transactionService;

    /**
     * Constructor that accepts ProductDAO and TransactionService parameters
     * @param productDAO the ProductDAO instance to use
     * @param transactionService the TransactionService instance to use
     */
    public InventoryService(ProductDAO productDAO, TransactionService transactionService) {
        this.productDAO = productDAO;
        this.transactionService = transactionService;
    }

    /**
     * Increases the quantity of a product
     * @param productId the ID of the product to update
     * @param amount the amount to increase by
     * @param currentUser the user performing the action
     * @return true if the increase is successful, false otherwise
     */
    public boolean increaseQuantity(int productId, int amount, User currentUser) {
        try {
            // Check if current user has permission to update inventory
            if (currentUser == null || (!currentUser.isAdmin() && !currentUser.isStaff())) {
                System.err.println("Unauthorized: Only admin and staff users can update inventory");
                return false;
            }

            // Get the current product
            Product product = productDAO.findProductById(productId);
            if (product == null) {
                System.err.println("Product not found");
                return false;
            }

            // Update the quantity
            product.setQuantity(product.getQuantity() + amount);
            boolean result = productDAO.updateProduct(product);
            
            if (result) {
                // Log the inventory update action
                transactionService.logAction(currentUser.getUserId(), "INCREASE_INVENTORY", "products", productId, 
                    "Product " + product.getName() + " quantity increased by " + amount + " by " + currentUser.getUsername() + 
                    ". New quantity: " + product.getQuantity());
            }
            return result;
        } catch (Exception e) {
            System.err.println("Error increasing product quantity: " + e.getMessage());
            return false;
        }
    }

    /**
     * Decreases the quantity of a product
     * @param productId the ID of the product to update
     * @param amount the amount to decrease by
     * @param currentUser the user performing the action
     * @return true if the decrease is successful, false otherwise
     */
    public boolean decreaseQuantity(int productId, int amount, User currentUser) {
        try {
            // Check if current user has permission to update inventory
            if (currentUser == null || (!currentUser.isAdmin() && !currentUser.isStaff())) {
                System.err.println("Unauthorized: Only admin and staff users can update inventory");
                return false;
            }

            // Get the current product
            Product product = productDAO.findProductById(productId);
            if (product == null) {
                System.err.println("Product not found");
                return false;
            }

            // Check if there's enough quantity to decrease
            if (product.getQuantity() < amount) {
                System.err.println("Insufficient quantity. Current quantity: " + product.getQuantity() + ", requested decrease: " + amount);
                return false;
            }

            // Update the quantity
            product.setQuantity(product.getQuantity() - amount);
            boolean result = productDAO.updateProduct(product);
            
            if (result) {
                // Log the inventory update action
                transactionService.logAction(currentUser.getUserId(), "DECREASE_INVENTORY", "products", productId, 
                    "Product " + product.getName() + " quantity decreased by " + amount + " by " + currentUser.getUsername() + 
                    ". New quantity: " + product.getQuantity());
            }
            return result;
        } catch (Exception e) {
            System.err.println("Error decreasing product quantity: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves products with quantity below a specified threshold
     * @param threshold the quantity threshold
     * @return a list of products with low stock
     */
    public List<Product> getLowStockItems(int threshold) {
        try {
            return productDAO.findProductsWithLowStock(threshold);
        } catch (Exception e) {
            System.err.println("Error retrieving low stock items: " + e.getMessage());
            return null;
        }
    }
}