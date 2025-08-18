CREATE TABLE employees (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           email VARCHAR(100) NOT NULL UNIQUE,
                           department VARCHAR(50),
                           joining_date DATE NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE leave_balances (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                employee_id BIGINT NOT NULL UNIQUE,
                                vacation_days INT NOT NULL DEFAULT 20,
                                sick_days INT NOT NULL DEFAULT 10,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);

CREATE TABLE leave_requests (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                employee_id BIGINT NOT NULL,
                                leave_type ENUM('VACATION', 'SICK', 'UNPAID') NOT NULL,
                                start_date DATE NOT NULL,
                                end_date DATE NOT NULL,
                                reason TEXT,
                                status ENUM('PENDING', 'APPROVED', 'REJECTED') NOT NULL DEFAULT 'PENDING',
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);
