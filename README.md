# 🏦 Banking Microservices System

This project is a full-fledged **Banking Microservices Application** built using **Spring Boot**, **Spring Cloud**, and **Netflix Eureka** for service discovery. It includes authentication, account management, and transaction tracking services behind an API Gateway.

---

## 📦 Microservices Overview

### 1. **Gateway Service**

* Acts as the API Gateway using Spring Cloud Gateway.
* Routes external requests to respective microservices.
* Port: `9090`

### 2. **User Service**

* Handles user registration, login, and JWT-based authentication.
* Contains security filters and configurations.
* Communicates with Account and Transaction services.
* Port : `8081`

### 3. **Account Service**

* Manages user accounts.
* Secured endpoints, integrates with User Service using Feign Client.
* Port : e.g., `8082`

### 4. **Transaction Service**

* Handles deposits, withdrawals, and transaction history.
* Interacts with both Account and User services.
* Port : `8083`

### 5. **Eureka Server**

* Service registry and discovery.
* Registers all microservices.
* Port: `8084`

---

## 🛠 Tech Stack

* **Java 17**
* **Spring Boot 3+**
* **Spring Cloud 2024.x+**
* **Spring Security + JWT**
* **Spring Cloud Gateway**
* **Spring Cloud Netflix Eureka**
* **Feign Clients**
* **Maven**
* **Postman**

---

## 📁 Project Structure

```bash
banking-microservices
│
├── gateway-service
│   └── application.yaml
│
├── user-service
│   ├── controller
│   ├── service
│   ├── security
│   └── dto
│
├── account-service
│   ├── controller
│   ├── service
│   └── feign
│
├── transaction-service
│   ├── controller
│   ├── service
│   └── feign
│
└── eureka-server
    └── application.yaml
```

---

## ⚙️ How to Run

1. Start the Eureka Server
2. Start the Gateway Service
3. Start User, Account, and Transaction services
4. Access API via Postman or any client through Gateway on port `9090`

---

## 🔐 Authentication Flow

* User registers and logs in via `/auth/register` and `/auth/login`
* Receives JWT token
* All other services (Account, Transaction) require this token

---

## 📄 Sample application.yaml for Gateway

```yaml
spring:
  application:
    name: gateway-service

  cloud:
    gateway:
      server:
        webflux:
          routes:
            - id: user-service-auth
              uri: lb://user-service
              predicates:
                - Path=/auth/**

            - id: user-service-user
              uri: lb://user-service
              predicates:
                - Path=/user/**

            - id: transaction-service
              uri: lb://transaction-service
              predicates:
                - Path=/api/transaction/**

            - id: account-service
              uri: lb://account-service
              predicates:
                - Path=/api/account/**

server:
  port: 9090

eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:8084/eureka/
```

---

## 🔗 Useful Links

* 🔗 GitHub Repository: https://github.com/kasatejaswarreddy0512/BankApp_Microservices
* 🛡️ JWT Tools: [https://jwt.io](https://jwt.io)

---

Feel free to contribute or customize this system for your own banking or finance-based applications!
