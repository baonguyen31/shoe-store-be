-- ÉP KIỂU MÃ HÓA TIẾNG VIỆT
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

-- XÓA DỮ LIỆU CŨ (Để nạp mới hoàn toàn)
SET FOREIGN_KEY_CHECKS = 0;
SET SQL_SAFE_UPDATES = 0;
DELETE FROM cart_items;
DELETE FROM orders_details;
DELETE FROM product_variants;
DELETE FROM products;
DELETE FROM orders;
DELETE FROM brands;
DELETE FROM categories;
DELETE FROM customers;
DELETE FROM employers;
SET FOREIGN_KEY_CHECKS = 1;
SET SQL_SAFE_UPDATES = 1;

-- 1. SEED CHO BẢNG CATEGORIES
INSERT INTO categories (id, name, created_at) VALUES 
(1, 'Giày Thể Thao Nam', '2026-04-01 08:00:00'),
(2, 'Giày Thể Thao Nữ', '2026-04-01 08:00:00'),
(3, 'Giày Cặp', '2026-04-01 08:00:00');

-- 2. SEED CHO BẢNG BRANDS
INSERT INTO brands (id, name, category_id, created_at) VALUES 
(1, 'Nike', 1, '2026-04-05 09:00:00'),
(2, 'Adidas', 2, '2026-04-05 09:10:00'),
(3, 'Puma', 1, '2026-04-05 09:20:00'),
(4, 'Converse', 1, '2026-04-05 09:30:00'),
(5, 'Vans', 2, '2026-04-05 09:40:00'),
(6, 'New Balance', 1, '2026-04-05 09:50:00'),
(7, 'Reebok', 2, '2026-04-05 10:00:00'),
(8, 'Jordan', 1, '2026-04-05 10:10:00'),
(9, 'Biti''s Hunter', 3, '2026-04-05 10:20:00'),
(10, 'MLB', 3, '2026-04-05 10:30:00');

-- 3. SEED CHO BẢNG PRODUCTS (Đã loại bỏ các sản phẩm theo yêu cầu)
INSERT INTO products (id, name, description, price, quantity, image_url, brand_id, category_id, deleted, discount_percentage, rating) VALUES 
(1, 'Nike Air Max 270', 'Giày chạy bộ cực êm với đế khí thế hệ mới', 100000.00, 49, 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?q=80&w=800', 1, 1, 0, NULL, NULL),
(2, 'Adidas Ultraboost 22', 'Hoàn trả năng lượng tối ưu cho mỗi bước chạy', 120000.00, 30, 'https://images.unsplash.com/photo-1587563871167-1ee9c731aefb?q=80&w=800', 2, 2, 0, NULL, NULL),
(3, 'Puma Suede Classic', 'Mẫu giày da lộn huyền thoại của Puma', 90000.00, 45, 'https://images.unsplash.com/photo-1741627872564-7d4b1cbd91ac?q=80&w=1171&auto=format&fit=crop', 3, 3, 0, NULL, NULL),
(4, 'Converse Chuck Taylor 70', 'Giày vải canvas phong cách vintage', 80000.00, 100, 'https://images.unsplash.com/photo-1491553895911-0055eca6402d?q=80&w=800', 4, 1, 0, NULL, NULL),
(5, 'Nike Air Force 1', 'Biểu tượng của phong cách đường phố', 110000, 50, 'https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?q=80&w=800', 1, 1, 0, NULL, NULL),
(8, 'Nike Dunk Low Panda', 'Sự kết hợp tối giản đen và trắng', 130000, 20, 'https://images.unsplash.com/photo-1605348532760-6753d2c43329?q=80&w=800', 1, 1, 0, NULL, NULL),
(9, 'Puma RS-X', 'Công nghệ đệm RS tiên tiến, cực êm', 105000, 30, 'https://images.unsplash.com/photo-1608231387042-66d1773070a5?q=80&w=800', 3, 1, 0, NULL, NULL),
(11, 'Vans Old Skool Blue', 'Giày trượt ván kinh điển với tông xanh', 85000, 60, 'https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77?q=80&w=800', 5, 1, 0, NULL, NULL),
(13, 'Nike Zoom Fly 5', 'Giày chạy bộ chuyên nghiệp cho marathon', 150000, 25, 'https://images.unsplash.com/photo-1543163521-1bf539c55dd2?q=80&w=800', 1, 1, 0, NULL, NULL),
(16, 'Adidas Stan Smith', 'Đôi giày tối giản nhất mọi thời đại', 95000, 50, 'https://images.unsplash.com/photo-1514989940723-e8e51635b782?q=80&w=800', 2, 2, 0, NULL, NULL),
(17, 'Puma Cali Star', 'Cảm hứng từ bờ biển California đầy nắng', 88000, 45, 'https://images.unsplash.com/photo-1560769629-975ec94e6a86?q=80&w=800', 3, 2, 0, NULL, NULL),
(18, 'Vans Authentic Cream', 'Thiết kế đơn giản cho mọi trang phục', 75000, 70, 'https://images.unsplash.com/photo-1560072810-1cffb09faf0f?q=80&w=800', 5, 2, 0, NULL, NULL),
(19, 'Nike Court Vision Low', 'Dáng vẻ bóng rổ trong phong cách hàng ngày', 92000, 55, 'https://images.unsplash.com/photo-1600185365483-26d7a4cc7519?q=80&w=800', 1, 2, 0, NULL, NULL),
(20, 'New Balance 574 Women', 'Cổ điển và thoải mái tuyệt đối', 100000, 40, 'https://images.unsplash.com/photo-1539185441755-769473a23570?q=80&w=800', 6, 2, 0, NULL, NULL),
(22, 'Puma Mayze Wedge', 'Đế cao hack chiều cao cực chuẩn', 115000, 30, 'https://images.unsplash.com/photo-1605408499391-6368c628ef42?q=80&w=800', 3, 2, 0, NULL, NULL),
(24, 'Nike Zoom Air Pegasus', 'Hỗ trợ chạy bộ tối ưu cho phái đẹp', 125000, 20, 'https://images.unsplash.com/photo-1515955656352-a1fa3ffcd111?q=80&w=800', 1, 2, 0, NULL, NULL),
(25, 'Nike Air Jordan 1 Pair', 'Cặp đôi huyền thoại cho chàng và nàng', 250000, 10, 'https://images.unsplash.com/photo-1551107696-a4b0c5a0d9a2?q=80&w=800', 8, 3, 0, NULL, NULL),
(26, 'Adidas Superstar Couple', 'Mũi giày vỏ sò gắn kết tình yêu', 180000, 15, 'https://images.unsplash.com/photo-1595341888016-a392ef81b7de?q=80&w=800', 2, 3, 0, NULL, NULL),
(27, 'Vans Slip-On Classic Pair', 'Tiện lợi và thời trang cho cả hai', 140000, 20, 'https://images.unsplash.com/photo-1549298916-b41d501d3772?q=80&w=800', 5, 3, 0, NULL, NULL),
(28, 'Biti''s Hunter Street Pair', 'Tự hào thương hiệu Việt, bền bỉ', 90000, 30, 'https://images.unsplash.com/photo-1511556532299-8f662fc26c06?q=80&w=800', 9, 3, 0, NULL, NULL),
(31, 'Nike Blazer Mid Pair', 'Kiểu dáng vintage cá tính cho couple', 160000, 10, 'https://images.unsplash.com/photo-1552346154-21d32810aba3?q=80&w=800', 1, 3, 0, NULL, NULL),
(32, 'Adidas Gazelle Pair', 'Chất liệu da lộn sang trọng', 155000, 18, 'https://images.unsplash.com/photo-1579338559194-a162d19bf842?q=80&w=800', 2, 3, 0, NULL, NULL),
(33, 'New Balance 327 Pair', 'Thiết kế chữ N lớn phá cách', 190000, 10, 'https://images.unsplash.com/photo-1460353581641-37baddab0fa2?q=80&w=800', 6, 3, 0, NULL, NULL),
(34, 'Puma Roma Classic Pair', 'Vẻ đẹp cổ điển từ nước Đức', 135000, 15, 'https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?q=80&w=800', 3, 3, 0, NULL, NULL);

-- 4. SEED CHO BẢNG PRODUCT_VARIANTS (Thay thế cho product_colors)
-- Giả định mỗi màu có các size từ 39-43 với số lượng mặc định
INSERT INTO product_variants (product_id, color, size, quantity) VALUES 
(1, 'Đen', '39', 10), (1, 'Đen', '40', 10), (1, 'Đen', '41', 10), (1, 'Đen', '42', 10), (1, 'Đen', '43', 9),
(1, 'Trắng', '39', 5), (1, 'Trắng', '40', 5), (1, 'Trắng', '41', 5), (1, 'Trắng', '42', 5), (1, 'Trắng', '43', 5),
(2, 'Trắng', '39', 10), (2, 'Trắng', '40', 10), (2, 'Trắng', '41', 10),
(2, 'Đỏ', '39', 5), (2, 'Đỏ', '40', 5),
(3, 'Nâu', '41', 15), (3, 'Nâu', '42', 15),
(3, 'Kem', '41', 10), (3, 'Kem', '42', 5),
(4, 'Trắng', '39', 20), (4, 'Trắng', '40', 20), (4, 'Trắng', '41', 20), (4, 'Trắng', '42', 20), (4, 'Trắng', '43', 20),
(5, 'Trắng', '39', 10), (5, 'Trắng', '40', 10), (5, 'Trắng', '41', 10),
(5, 'Đen', '39', 10), (5, 'Đen', '40', 10),
(8, 'Đen', '39', 5), (8, 'Đen', '40', 5),
(8, 'Trắng', '39', 5), (8, 'Trắng', '40', 5),
(9, 'Xám', '41', 15), (9, 'Đỏ', '41', 15),
(11, 'Be', '39', 30), (11, 'Trắng', '39', 30),
(13, 'Đỏ', '42', 10), (13, 'Trắng', '42', 15),
(16, 'Trắng', '39', 25), (16, 'Kem', '39', 25),
(17, 'Kem', '38', 20), (17, 'Trắng', '38', 25),
(18, 'Trắng', '37', 35), (18, 'Nâu', '37', 35),
(19, 'Trắng', '39', 25), (19, 'Đen', '39', 30),
(20, 'Xám', '38', 20), (20, 'Kem', '38', 20),
(22, 'Kem', '37', 15), (22, 'Trắng', '37', 15),
(24, 'Đỏ', '38', 10), (24, 'Trắng', '38', 10),
(25, 'Đỏ', '40', 5), (25, 'Đen', '40', 5),
(26, 'Trắng', '41', 8), (26, 'Đen', '41', 7),
(27, 'Trắng', '39', 10), (27, 'Xám', '39', 10),
(28, 'Be', '42', 15), (28, 'Xám', '42', 15),
(31, 'Trắng', '41', 5), (31, 'Đỏ', '41', 5),
(32, 'Nâu', '40', 9), (32, 'Be', '40', 9),
(33, 'Xám', '42', 5), (33, 'Trắng', '42', 5),
(34, 'Trắng', '41', 8), (34, 'Kem', '41', 7);

-- 5. SEED CHO BẢNG CUSTOMERS
INSERT INTO customers (id, username, password, full_name, email, phone, street, ward, city, status, created_at, date_of_birth) VALUES 
(1, 'hahodat1958@gmail.com', '$2a$12$oTc8gPWL89pAAYHqie.FcuvIWdgbqZLgEN7RNYCuckCSsXuPDC0Vu', 'quốc Đạt', 'hahodat1958@gmail.com', '0769505807', '138 nguyen van cu', '1', 'Ho Chi Minh', 1, '2026-05-01 09:40:05', '2026-05-09'),
(2, 'user2@gmail.com', '$2a$12$oTc8gPWL89pAAYHqie.FcuvIWdgbqZLgEN7RNYCuckCSsXuPDC0Vu', 'Trần Thị Người Dùng', 'user2@gmail.com', '0918889990', '456 Tây Sơn', 'Phường Quang Trung', 'Hà Nội', 1, '2026-05-01 10:00:00', NULL),
(3, 'user3@gmail.com', '$2a$12$oTc8gPWL89pAAYHqie.FcuvIWdgbqZLgEN7RNYCuckCSsXuPDC0Vu', 'Lê Hoàng Cường', 'user3@gmail.com', '0923456789', '78 Nguyễn Văn Linh', 'Phường Nam Dương', 'Đà Nẵng', 1, '2026-05-01 10:30:00', NULL);

-- 6. SEED CHO BẢNG EMPLOYERS
INSERT INTO employers (id, username, password, full_name, email, phone, street, ward, city, salary, status, created_at, date_of_birth) VALUES 
(1, 'admin@gmail.com', '$2a$12$oTc8gPWL89pAAYHqie.FcuvIWdgbqZLgEN7RNYCuckCSsXuPDC0Vu', 'Admin Hệ Thống', 'admin@gmail.com', '0988888888', '789 Nguyễn Huệ', 'Phường Bến Nghé', 'TP.HCM', 20000000.00, 1, '2026-04-01 08:00:00', NULL),
(2, 'staff@gmail.com', '$2a$12$oTc8gPWL89pAAYHqie.FcuvIWdgbqZLgEN7RNYCuckCSsXuPDC0Vu', 'Nhân Viên Bán Hàng', 'staff@gmail.com', '0977777777', '101 Trần Phú', 'Phường Hải Châu', 'Đà Nẵng', 10000000.00, 1, '2026-04-10 08:00:00', NULL);

-- 7. SEED CHO BẢNG ORDERS
INSERT INTO orders (id, customer_id, total_price, status, method, employer_id, city, full_name, phone, street, ward, payment_status, created_at, updated_at, deleted) VALUES 
(1, 1, 100000.00, 'PENDING', 'COD', 1, NULL, NULL, NULL, NULL, NULL, 'PENDING', '2026-05-01 10:00:00', '2026-05-01 10:00:00', 0),
(2, 2, 90000.00, 'DELIVERED', 'MOMO', 1, NULL, NULL, NULL, NULL, NULL, 'PAID', '2026-05-01 11:00:00', '2026-05-01 11:00:00', 0),
(6, 3, 125000.00, 'PENDING', 'COD', NULL, 'Ho Chi Minh', 'quốc Đạt', '0769505807', 'NG', '1', 'PENDING', '2026-05-01 10:24:45', '2026-05-01 10:24:45', 0),
(7, 3, 105000.00, 'DELIVERED', 'MOMO', 1, 'Ho Chi Minh', 'quốc Đạt', '0769505807', '138 nguyen van cu', '1', 'PAID', '2026-05-01 10:25:11', '2026-05-01 10:55:45', 0);

-- 8. SEED CHO BẢNG ORDERS_DETAILS
INSERT INTO orders_details (order_id, product_id, quantity, cost, total) VALUES 
(1, 1, 1, 100000.00, 100000.00),
(2, 3, 1, 90000.00, 90000.00),
(6, 2, 1, 120000.00, 120000.00),
(7, 1, 1, 100000.00, 100000.00);

-- 9. SEED CHO BẢNG CART_ITEMS
INSERT INTO cart_items (id, color, quantity, size, customer_id, product_id) VALUES
(6, 'Đen', 1, '39', 3, 1);