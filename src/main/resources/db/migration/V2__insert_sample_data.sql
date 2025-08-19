-- Insert employees (no HR department)
INSERT INTO employees (name, email, department, joining_date) VALUES
                                                                  ('Amit Sharma', 'amit.sharma@example.com', 'IT', '2023-01-10'),
                                                                  ('Priya Verma', 'priya.verma@example.com', 'Finance', '2022-08-15'),
                                                                  ('Rohit Gupta', 'rohit.gupta@example.com', 'Finance', '2021-05-20'),
                                                                  ('Sneha Joshi', 'sneha.joshi@example.com', 'Marketing', '2023-03-01'),
                                                                  ('Vikram Singh', 'vikram.singh@example.com', 'Operations', '2022-11-12');

-- Insert leave balances
INSERT INTO leave_balances (employee_id, vacation_days, sick_days) VALUES
                                                                       (1, 20, 10),
                                                                       (2, 18, 12),
                                                                       (3, 25, 15),
                                                                       (4, 22, 10),
                                                                       (5, 20, 10);

-- Insert leave requests
INSERT INTO leave_requests (employee_id, leave_type, start_date, end_date, reason, status) VALUES
                                                                                               (1, 'VACATION', '2025-09-01', '2025-09-05', 'Family trip', 'PENDING'),
                                                                                               (2, 'SICK', '2025-08-20', '2025-08-22', 'Fever', 'APPROVED'),
                                                                                               (3, 'VACATION', '2025-10-10', '2025-10-15', 'Wedding', 'PENDING'),
                                                                                               (4, 'UNPAID', '2025-08-25', '2025-08-26', 'Personal work', 'REJECTED'),
                                                                                               (5, 'VACATION', '2025-09-15', '2025-09-20', 'Vacation', 'PENDING');

-- Insert one HR user
INSERT INTO hr_users (username, password) VALUES
    ('hr_admin', '$2a$12$eBFB.KVFAG9Cipkbs5c5GeyMpeihphrVYKd614.ktzSRjsmuw21wa');

