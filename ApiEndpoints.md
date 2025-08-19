# Leave Management System - API Endpoints

## Authentication

### Login

* **URL:** `POST http://localhost:8080/auth/login`
* **Request Body:**

```json
{
  "username": "hr_admin",
  "password": "password123"
}
```

* **Response:**

```json
{
  "message": "Login successful",
  "name": "hr_admin",
  "token": "<JWT_TOKEN>"
}
```

## Employee Management

### Add New Employee

* **URL:** `POST http://localhost:8080/api/hr/add-employee`
* **Headers:**

```
Authorization: Bearer <JWT_TOKEN>
```

* **Request Body:**

```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "department": "Engineering",
  "joiningDate": "2025-09-01"
}
```

* **Response:**

```json
{
  "id": 6,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "department": "Engineering",
  "joiningDate": "2025-09-01"
}
```

## Leave Management

### Apply for Leave

* **URL:** `POST http://localhost:8080/api/hr/apply-leave`
* **Headers:**

```
Authorization: Bearer <JWT_TOKEN>
```

* **Request Body:**

```json
{
  "employeeId": 6,
  "leaveType": "VACATION",
  "startDate": "2025-09-10",
  "endDate": "2025-09-15",
  "reason": "Family trip"
}
```

* **Response:**

```json
{
  "id": 7,
  "employeeId": 6,
  "leaveType": "VACATION",
  "status": "PENDING",
  "startDate": "2025-09-10",
  "endDate": "2025-09-15",
  "reason": "Family trip"
}
```

### Approve/Reject Leave

* **URL:** `POST http://localhost:8080/api/hr/leave/update-status`
* **Headers:**

```
Authorization: Bearer <JWT_TOKEN>
```

* **Request Body:**

```json
{
  "leaveRequestId": 7,
  "action": "APPROVE"
}
```

* **Response:**

```json
{
  "id": 7,
  "employeeId": 6,
  "leaveType": "VACATION",
  "status": "APPROVED",
  "startDate": "2025-09-10",
  "endDate": "2025-09-15",
  "reason": "Family trip"
}
```

### Fetch Leave Balance

* **URL:** `GET http://localhost:8080/api/hr/leave-balance/{employeeId}`
* **Headers:**

```
Authorization: Bearer <JWT_TOKEN>
```

* **Response:**

```json
{
  "employeeId": 6,
  "vacationDays": 15,
  "sickDays": 10
}
```
