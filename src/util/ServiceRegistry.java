package util;

import java.util.Map;
import java.util.HashMap;

/**
 * ServiceRegistry implements a simple dependency injection container
 * using the Singleton pattern.
 */
public class ServiceRegistry {
    // Singleton instance
    private static ServiceRegistry instance;
    
    // Map to store registered services
    private Map<Class<?>, Object> services;
    
    /**
     * Private constructor to prevent instantiation
     */
    private ServiceRegistry() {
        services = new HashMap<>();
    }
    
    /**
     * Returns the singleton instance of ServiceRegistry
     * @return the ServiceRegistry instance
     */
    public static synchronized ServiceRegistry getInstance() {
        if (instance == null) {
            instance = new ServiceRegistry();
        }
        return instance;
    }
    
    /**
     * Registers a service with its class
     * @param serviceClass the class of the service
     * @param service the service instance
     * @param <T> the type of the service
     */
    public <T> void registerService(Class<T> serviceClass, T service) {
        services.put(serviceClass, service);
    }
    
    /**
     * Retrieves a registered service by its class
     * @param serviceClass the class of the service to retrieve
     * @param <T> the type of the service
     * @return the service instance
     * @throws RuntimeException if the service is not registered
     */
    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> serviceClass) {
        if (!services.containsKey(serviceClass)) {
            throw new RuntimeException("Service not registered: " + serviceClass.getName());
        }
        return (T) services.get(serviceClass);
    }
}