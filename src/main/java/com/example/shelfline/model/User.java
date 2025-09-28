package com.example.shelfline.model;

import javax.persistence.*;

/**
 * User entity representing a system user.
 * Contains information about users including identification, username, password, and role.
 * 
 * @author ShelfLine Team
 * @version 1.0
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;
    
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "role", nullable = false)
    private String role;

    /**
     * Default constructor for User.
     * Initializes a new User with default values.
     */
    public User() {
    }

    /**
     * Constructor for User with all fields.
     * 
     * @param id the unique identifier for the user
     * @param username the username of the user
     * @param password the password of the user
     * @param role the role of the user (admin/staff)
     */
    public User(long id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Constructor for User without id (for new users).
     * 
     * @param username the username of the user
     * @param password the password of the user
     * @param role the role of the user (admin/staff)
     */
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Gets the unique identifier for the user.
     * 
     * @return the user id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the user.
     * 
     * @param id the user id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the username of the user.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     * 
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the role of the user.
     * 
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     * 
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returns a string representation of the User.
     * 
     * @return a string representation of the User
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    /**
     * Compares this User to the specified object for equality.
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
        User user = (User) obj;
        return id == user.id &&
                username.equals(user.username) &&
                role.equals(user.role);
    }

    /**
     * Returns a hash code value for the User.
     * 
     * @return a hash code value for this User
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        return result;
    }
}