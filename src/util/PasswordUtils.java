package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

/**
 * PasswordUtils utility class for handling password hashing and verification.
 * This class provides methods to securely hash passwords and verify them against stored hashes.
 */
public class PasswordUtils {
    
    // Fixed salt for this implementation (in production, each password would have its own random salt)
    private static final String SALT = "inventory_system_salt_2025";
    
    /**
     * Hashes a password using SHA-256 with a fixed salt.
     * 
     * @param password the plain text password to hash
     * @return the hashed password as a hexadecimal string
     */
    public static String hashPassword(String password) {
        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            // Combine password with salt
            String saltedPassword = password + SALT;
            
            // Hash the salted password
            byte[] hashBytes = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
            
            // Convert byte array to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // This should never happen as SHA-256 is a standard algorithm
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
    
    /**
     * Verifies a password against a hash.
     * 
     * @param password the plain text password to verify
     * @param hash the stored hash to compare against
     * @return true if the password matches the hash, false otherwise
     */
    public static boolean verifyPassword(String password, String hash) {
        // Hash the provided password and compare with the stored hash
        String hashedPassword = hashPassword(password);
        return hashedPassword.equals(hash);
    }
}