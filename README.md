# 🚀 Subscription Billing System

---

# 🧑‍💼 Non-Technical Explanation (Simple Understanding)

This project is a **Subscription Billing System**, similar to platforms like Netflix, Amazon Prime, or mobile recharge systems.

### 📌 What does this system do?

It helps businesses manage:

* Customers (users)
* Subscription plans (monthly/yearly)
* Billing (invoices)
* Payments

---

### 🔄 How does it work?

1. A user signs up
2. The company creates subscription plans
3. The user selects a plan
4. The system generates a bill automatically
5. The user makes payment
6. Subscription becomes active
7. After expiry, user can renew or upgrade

---

### 💡 Real-Life Example

If you buy a **Netflix subscription**:

* You choose a plan
* You pay
* You get access for a month
* After expiry, you renew

👉 This system works exactly like that.

---

### 🎯 Why is this useful?

* Automates billing process
* Tracks payments
* Saves time
* Reduces manual work
* Provides business insights

---

# 🧠 Technical Overview

## 📌 Architecture

This project follows a **layered architecture**:

```id="arch1"
Controller → Service → Repository → Database
```

### Layers:

* **Controller** → Handles API requests
* **Service** → Business logic
* **Repository** → Database interaction
* **DTO** → Data transfer objects
* **Entity** → Database tables

---

## 🏗️ Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA
* MySQL
* Lombok
* Swagger (API Docs)
* SLF4J Logging

---

## 📁 Project Structure

```id="arch2"
com.billing.system
│
├── controller
├── service
├── repository
├── entity
├── dto
├── exception
```

---

# 🗄️ Database Schema

```sql id="schema1"
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100),
    role VARCHAR(20),
    status VARCHAR(20),
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE plan (
    plan_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    plan_name VARCHAR(100),
    description VARCHAR(255),
    price DOUBLE,
    billing_cycle VARCHAR(20),
    status VARCHAR(20),
    created_at DATETIME
);

CREATE TABLE subscription (
    subscription_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    plan_id BIGINT,
    start_date DATETIME,
    end_date DATETIME,
    status VARCHAR(20),
    created_at DATETIME
);

CREATE TABLE invoice (
    invoice_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    subscription_id BIGINT,
    user_id BIGINT,
    amount DOUBLE,
    due_date DATETIME,
    paid_date DATETIME,
    status VARCHAR(20),
    created_at DATETIME
);

CREATE TABLE payment (
    payment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    invoice_id BIGINT,
    user_id BIGINT,
    amount DOUBLE,
    payment_method VARCHAR(50),
    payment_date DATETIME,
    status VARCHAR(20)
);

CREATE TABLE audit_log (
    log_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    action VARCHAR(50),
    entity VARCHAR(50),
    entity_id BIGINT,
    timestamp DATETIME
);
```

---

# 🔄 System Workflow

```id="flow1"
User → Register
↓
Plan → Created by Admin
↓
User → Subscribe
↓
Subscription → Created
↓
Invoice → Auto Generated
↓
Payment → Done
↓
Invoice → PAID
↓
Subscription → Active / Expired
```

---

# 📡 API Endpoints

## 👤 User APIs

* POST `/api/users/register`
* GET `/api/users/{id}`
* GET `/api/users?page=0&size=5&search=`
* PUT `/api/users/{id}`
* DELETE `/api/users/{id}`

---

## 📦 Plan APIs

* POST `/api/plans`
* GET `/api/plans`
* GET `/api/plans/{id}`
* PUT `/api/plans/{id}`
* DELETE `/api/plans/{id}`

---

## 📑 Subscription APIs

* POST `/api/subscriptions`
* GET `/api/subscriptions/{id}`
* GET `/api/subscriptions/user/{userId}`
* GET `/api/subscriptions?status=ACTIVE`
* PUT `/api/subscriptions/cancel/{id}`
* PUT `/api/subscriptions/upgrade/{id}`

---

## 🧾 Invoice APIs

* GET `/api/invoices/{id}`
* GET `/api/invoices/user/{userId}`
* GET `/api/invoices?status=PENDING`
* PUT `/api/invoices/{id}/status`

---

## 💳 Payment APIs

* POST `/api/payments`
* GET `/api/payments/{id}`
* GET `/api/payments/user/{userId}`
* GET `/api/payments/invoice/{invoiceId}`

---

## 📊 Dashboard API

* GET `/api/dashboard/summary`

---

## 📝 Audit Logs

* GET `/api/audit`
* GET `/api/audit/user/{userId}`

---

# 🪵 Logging

* INFO → Business flow
* DEBUG → Internal details
* ERROR → Exceptions

---

# ⚠️ Exception Handling

* 404 → Resource Not Found
* 400 → Bad Request
* 500 → Internal Server Error

---

# 🔍 Swagger UI

```id="swagger1"
http://localhost:8080/swagger-ui/index.html
```

---

# 🚀 How to Run

1. Clone project
2. Create DB → `billing_db`
3. Update `application.properties`
4. Run Spring Boot app
5. Test APIs via Postman / Swagger

---

# 📊 Project Status

✅ Backend Completed
✅ APIs Implemented
✅ Dashboard Added
✅ Logging & Exception Handling
✅ Ready for Testing

---

# 🚀 Future Enhancements

* JWT Authentication
* Email Notifications
* Payment Gateway Integration
* Scheduler for auto expiry

---

# 👨‍💻 Author

**Tarun Yendu**
Full Stack Developer
