DROP DATABASE IF EXISTS sales_management;
CREATE DATABASE sales_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE sales_management;

-- Bảng người dùng hệ thống (đăng nhập / phân quyền)
CREATE TABLE users (
                       id              BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username        VARCHAR(50)  NOT NULL UNIQUE,
                       password_hash   VARCHAR(255) NOT NULL,
                       full_name       VARCHAR(100) NOT NULL,
                       role            ENUM('ADMIN', 'STAFF') NOT NULL DEFAULT 'STAFF',
                       active          BOOLEAN NOT NULL DEFAULT TRUE,
                       created_by      VARCHAR(100) NULL,
                       created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_by      VARCHAR(100) NULL,
                       updated_at      DATETIME NULL ON UPDATE CURRENT_TIMESTAMP
);

-- Danh mục sản phẩm
CREATE TABLE categories (
                            id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name        VARCHAR(100) NOT NULL UNIQUE,
                            description VARCHAR(255),
                            created_by  VARCHAR(100) NULL,
                            created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            updated_by  VARCHAR(100) NULL,
                            updated_at  DATETIME NULL ON UPDATE CURRENT_TIMESTAMP
);

-- Sản phẩm
CREATE TABLE products (
                          id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name        VARCHAR(150) NOT NULL,
                          category_id BIGINT NOT NULL,
                          price       DECIMAL(15,2) NOT NULL,
                          quantity    INT NOT NULL DEFAULT 0,
                          created_by  VARCHAR(100) NULL,
                          created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_by  VARCHAR(100) NULL,
                          updated_at  DATETIME NULL ON UPDATE CURRENT_TIMESTAMP,
                          CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Khách hàng
CREATE TABLE customers (
                           id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name        VARCHAR(100) NOT NULL,
                           phone       VARCHAR(20)  NOT NULL UNIQUE,
                           email       VARCHAR(100),
                           address     VARCHAR(255),
                           created_by  VARCHAR(100) NULL,
                           created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_by  VARCHAR(100) NULL,
                           updated_at  DATETIME NULL ON UPDATE CURRENT_TIMESTAMP
);

-- Đơn hàng
CREATE TABLE orders (
                        id            BIGINT AUTO_INCREMENT PRIMARY KEY,
                        customer_id   BIGINT NOT NULL,
                        user_id       BIGINT NOT NULL,
                        order_date    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        status        ENUM('PENDING', 'COMPLETED', 'CANCELLED') NOT NULL DEFAULT 'PENDING',
                        total_amount  DECIMAL(15,2) NOT NULL DEFAULT 0,
                        created_by    VARCHAR(100) NULL,
                        created_at    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_by    VARCHAR(100) NULL,
                        updated_at    DATETIME NULL ON UPDATE CURRENT_TIMESTAMP,
                        CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id) REFERENCES customers(id),
                        CONSTRAINT fk_orders_user     FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Chi tiết đơn hàng
CREATE TABLE order_details (
                               id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                               order_id    BIGINT NOT NULL,
                               product_id  BIGINT NOT NULL,
                               quantity    INT NOT NULL,
                               unit_price  DECIMAL(15,2) NOT NULL,
                               created_by  VARCHAR(100) NULL,
                               created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               updated_by  VARCHAR(100) NULL,
                               updated_at  DATETIME NULL ON UPDATE CURRENT_TIMESTAMP,
                               CONSTRAINT fk_od_order   FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                               CONSTRAINT fk_od_product FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Dữ liệu mẫu
-- Mật khẩu mẫu: "admin123" và "staff123"
INSERT INTO users (username, password_hash, full_name, role, created_by) VALUES
                                                                             ('admin', '600000:FstObSC3tYTfuJxOfZmTdA==:F8PaLc4iPjrpCuiNjxkCHlEuJD3Hy/kRurFPoLTlNx4=', 'Quản trị viên', 'ADMIN', 'system'),
                                                                             ('staff1', '600000:hIVHz1uGGUwmAmpPPpHm5w==:ZloOvdFKcVk84NimrDPVK5teFbr3jlGUlc7M+Qask9U=', 'Nhân viên bán hàng', 'STAFF', 'system');

INSERT INTO categories (name, description, created_by) VALUES
                                                           ('Điện thoại', 'Điện thoại di động các loại', 'system'),
                                                           ('Laptop', 'Máy tính xách tay', 'system'),
                                                           ('Phụ kiện', 'Phụ kiện điện tử', 'system');

INSERT INTO products (name, category_id, price, quantity, created_by) VALUES
                                                                          ('iPhone 15', 1, 22000000, 20, 'system'),
                                                                          ('Samsung Galaxy S24', 1, 19000000, 15, 'system'),
                                                                          ('MacBook Air M2', 2, 27000000, 10, 'system'),
                                                                          ('Dell XPS 13', 2, 25000000, 8, 'system'),
                                                                          ('Tai nghe AirPods Pro', 3, 5500000, 30, 'system'),
                                                                          ('Sạc dự phòng 20000mAh', 3, 550000, 50, 'system');

INSERT INTO customers (name, phone, email, address, created_by) VALUES
                                                                    ('Nguyễn Văn A', '0901111111', 'a.nguyen@email.com', 'Hà Nội', 'system'),
                                                                    ('Trần Thị B', '0902222222', 'b.tran@email.com', 'TP. Hồ Chí Minh', 'system'),
                                                                    ('Lê Văn C', '0903333333', 'c.le@email.com', 'Đà Nẵng', 'system');