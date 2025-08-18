-- Insert 20 employees
INSERT INTO employees (name, email, department, joining_date)
VALUES
    ('Alice Johnson', 'alice.johnson@example.com', 'HR', '2022-01-10'),
    ('Bob Smith', 'bob.smith@example.com', 'Engineering', '2021-03-15'),
    ('Charlie Brown', 'charlie.brown@example.com', 'Finance', '2020-07-20'),
    ('David Miller', 'david.miller@example.com', 'Engineering', '2023-02-05'),
    ('Evelyn Davis', 'evelyn.davis@example.com', 'HR', '2022-11-25'),
    ('Frank Wilson', 'frank.wilson@example.com', 'Marketing', '2019-09-12'),
    ('Grace Lee', 'grace.lee@example.com', 'Engineering', '2021-06-30'),
    ('Henry White', 'henry.white@example.com', 'Finance', '2020-12-18'),
    ('Ivy Taylor', 'ivy.taylor@example.com', 'Engineering', '2021-04-14'),
    ('Jack Harris', 'jack.harris@example.com', 'HR', '2022-08-22'),
    ('Karen Lewis', 'karen.lewis@example.com', 'Marketing', '2023-01-05'),
    ('Leo Young', 'leo.young@example.com', 'Engineering', '2020-03-10'),
    ('Mia King', 'mia.king@example.com', 'Finance', '2022-05-16'),
    ('Noah Scott', 'noah.scott@example.com', 'Engineering', '2021-07-19'),
    ('Olivia Green', 'olivia.green@example.com', 'Marketing', '2022-09-11'),
    ('Paul Adams', 'paul.adams@example.com', 'Engineering', '2019-11-27'),
    ('Quinn Baker', 'quinn.baker@example.com', 'Finance', '2021-02-03'),
    ('Rachel Carter', 'rachel.carter@example.com', 'HR', '2020-08-09'),
    ('Samuel Turner', 'samuel.turner@example.com', 'Engineering', '2021-12-21'),
    ('Tina Parker', 'tina.parker@example.com', 'Finance', '2022-10-30');

-- Insert default leave balances for all 20 employees
INSERT INTO leave_balances (employee_id, vacation_days, sick_days)
SELECT id, 20, 10 FROM employees;

-- Insert some initial leave requests for demo purposes
INSERT INTO leave_requests (employee_id, leave_type, start_date, end_date, reason, status)
VALUES
    (1, 'VACATION', '2023-12-01', '2023-12-05', 'Family trip', 'APPROVED'),
    (2, 'SICK', '2023-11-10', '2023-11-12', 'Flu', 'APPROVED'),
    (3, 'UNPAID', '2023-10-01', '2023-10-03', 'Personal reasons', 'REJECTED'),
    (4, 'VACATION', '2023-12-15', '2023-12-20', 'Holiday', 'PENDING'),
    (5, 'SICK', '2023-11-20', '2023-11-22', 'Fever', 'PENDING');
