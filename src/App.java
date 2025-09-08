public class App {
    public static void main(String[] args) throws Exception {
        // Initialize service registry
        util.ServiceRegistry registry = util.ServiceRegistry.getInstance();
        
        // Create and register database connection
        util.DatabaseConnection dbConnection = util.DatabaseConnection.getInstance();
        registry.registerService(util.DatabaseConnection.class, dbConnection);
        
        // Create and register DAOs
        dao.UserDAO userDAO = new dao.UserDAO(dbConnection);
        dao.ProductDAO productDAO = new dao.ProductDAO(dbConnection);
        dao.TransactionDAO transactionDAO = new dao.TransactionDAO(dbConnection);
        
        registry.registerService(dao.UserDAO.class, userDAO);
        registry.registerService(dao.ProductDAO.class, productDAO);
        registry.registerService(dao.TransactionDAO.class, transactionDAO);
        
        // Create and register services
        service.TransactionService transactionService = new service.TransactionService(transactionDAO);
        service.UserService userService = new service.UserService(userDAO, transactionService);
        service.ProductService productService = new service.ProductService(productDAO, transactionService);
        service.InventoryService inventoryService = new service.InventoryService(productDAO, transactionService);
        
        registry.registerService(service.TransactionService.class, transactionService);
        registry.registerService(service.UserService.class, userService);
        registry.registerService(service.ProductService.class, productService);
        registry.registerService(service.InventoryService.class, inventoryService);
        
        // Create authentication manager
        auth.AuthenticationManager authManager = new auth.AuthenticationManager(userService);
        
        // Create and start menu system
        ui.MenuSystem menuSystem = new ui.MenuSystem(authManager, productService, userService, inventoryService, transactionService);
        menuSystem.start();
    }
}
