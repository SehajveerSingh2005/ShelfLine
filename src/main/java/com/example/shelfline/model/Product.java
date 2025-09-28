package com.example.shelfline.model;

import javax.persistence.*;

/**
 * Product entity representing an item in the inventory system.
 * Contains information about products including identification, name, quantity, price, and category.
 * 
 * @author ShelfLine Team
 * @version 1.0
 */
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "quantity", nullable = false)
    private int quantity;
    
    @Column(name = "price", nullable = false)
    private double price;
    
    @Column(name = "category", nullable = false)
    private String category;

    /**
     * Default constructor for Product.
     * Initializes a new Product with default values.
     */
    public Product() {
    }

    /**
     * Constructor for Product with all fields.
     * 
     * @param id the unique identifier for the product
     * @param name the name of the product
     * @param quantity the quantity of the product in inventory
     * @param price the price of the product
     * @param category the category of the product
     */
    public Product(long id, String name, int quantity, double price, String category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    /**
     * Constructor for Product without id (for new products).
     * 
     * @param name the name of the product
     * @param quantity the quantity of the product in inventory
     * @param price the price of the product
     * @param category the category of the product
     */
    public Product(String name, int quantity, double price, String category) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    /**
     * Gets the unique identifier for the product.
     * 
     * @return the product id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the product.
     * 
     * @param id the product id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the name of the product.
     * 
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     * 
     * @param name the product name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the quantity of the product in inventory.
     * 
     * @return the product quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product in inventory.
     * 
     * @param quantity the product quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price of the product.
     * 
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     * 
     * @param price the product price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the category of the product.
     * 
     * @return the product category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the product.
     * 
     * @param category the product category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Returns a string representation of the Product.
     * 
     * @return a string representation of the Product
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }

    /**
     * Compares this Product to the specified object for equality.
     * 
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product product = (Product) obj;
        return id == product.id &&
                quantity == product.quantity &&
                Double.compare(product.price, price) == 0 &&
                name.equals(product.name) &&
                category.equals(product.category);
    }

    /**
     * Returns a hash code value for the Product.
     * 
     * @return a hash code value for this Product
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + quantity;
        long temp = Double.doubleToLongBits(price);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        return result;
    }
}