# Leave Management System

A **Spring Boot** backend API for managing employees and their leave requests. This project uses **JWT authentication** for HR users and supports leave management operations.

---

## 📝 Features

* HR login and JWT-based authentication.
* Add new employees.
* Apply for leave with validations:

    * Cannot apply before joining date.
    * Cannot apply for more days than available balance.
    * Prevent overlapping leave requests.
    * Validate end date is after start date.
    * Employee existence check.
* Approve or reject leave requests.
* Fetch leave balances for employees.
* Entities:

    * Employee
    * HrUser
    * LeaveBalance
    * LeaveRequest
* Enums:

    * LeaveStatus: PENDING, APPROVED, REJECTED
    * LeaveType: VACATION, SICK, UNPAID

---

## ⚙️ Setup Instructions

### Prerequisites

* Java 17+
* Maven
* MySQL (or AWS RDS instance)
* Docker (optional for running DB locally)
* Postman or any API client

### Steps

1. **Clone the repository**

   ```bash
   git clone <repo-url>
   cd leave-management-system
   ```

2. **Database setup**

    * Create a MySQL database.
    * Run `V1__init_schema.sql` to create tables.
    * Run `V2__insert_sample_data.sql` to insert sample data.
    * Update `application.properties` (or `.env`) with your DB credentials.

3. **Run the application**

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Test APIs**

    * Login HR: `POST /api/auth/login` with JSON `{ "username": "hr_admin", "password": "password123" }`
    * Add employee: `POST /api/hr/add-employee` with employee JSON (Authorization header: `Bearer <token>`)
    * Apply leave, approve/reject leave, fetch leave balance using respective endpoints.

---

## 🔑 Assumptions

* Only HR can manage employees and leave requests.
* Passwords are stored securely with encoding.
* No role management beyond HR (single type of user).
* Leave balances are automatically created for each new employee.
* JWT tokens are long-lived for simplicity.

---

## 💡 Potential Improvements

* Add token expiration and refresh mechanism.
* Introduce role-based access for multiple user types.
* Implement frontend application for better UI.
* Add email notifications for leave status updates.
* Implement rate limiting or API gateway for security.
* Add automated tests for all services and endpoints.
* Containerize entire application with Docker Compose for local dev.
* Use AWS Secrets Manager for storing DB credentials securely.
