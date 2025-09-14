-- Test data for Inventory Management System
-- All prices are in Indian Rupees (₹)

-- Insert test users
INSERT INTO users (username, password, role) VALUES 
('admin', 'admin123', 'admin'),
('staff', 'staff123', 'staff');

-- Insert test products (prices in INR)
INSERT INTO products (name, description, category, price, quantity) VALUES
('Laptop', 'High-performance laptop for work and gaming', 'Electronics', 99999.00, 15),
('Desk Chair', 'Ergonomic office chair with lumbar support', 'Furniture', 15999.00, 30),
('Wireless Mouse', 'Ergonomic wireless mouse with adjustable DPI', 'Electronics', 1499.00, 75),
('Notebook', 'Spiral-bound notebook with 100 pages', 'Stationery', 199.00, 200),
('Coffee Maker', 'Automatic drip coffee maker with programmable timer', 'Appliances', 4999.00, 25);