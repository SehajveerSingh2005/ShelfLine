package com.example.shelfline.repository;

import com.example.shelfline.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for Product entities.
 * Provides CRUD operations and additional query methods for products.
 * 
 * @author ShelfLine Team
 * @version 1.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * Finds products by category.
     * 
     * @param category the category to search for
     * @return a list of products in the specified category
     */
    List<Product> findByCategory(String category);
    
    /**
     * Finds products by name (partial match).
     * 
     * @param name the name pattern to search for
     * @return a list of products matching the name pattern
     */
    List<Product> findByNameContainingIgnoreCase(String name);
    
    /**
     * Finds products with quantity less than or equal to the specified threshold.
     * 
     * @param threshold the quantity threshold for low stock
     * @return a list of products with low stock
     */
    List<Product> findByQuantityLessThanEqual(int threshold);
    
    /**
     * Gets all distinct categories from the products table.
     * 
     * @return a list of all distinct categories
     */
    @Query("SELECT DISTINCT p.category FROM Product p ORDER BY p.category")
    List<String> findAllCategories();
}