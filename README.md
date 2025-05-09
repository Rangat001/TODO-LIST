# 📝 TODO-LIST Application

## 📌 Overview

The **TODO-LIST** application is a smart task management system that helps users efficiently **organize**, **track**, and **manage** their daily activities.  
Built with 💻 **Java (Spring Boot)** on the backend and 🌐 **HTML** on the frontend, it also features an ⏰ **email reminder system** for upcoming and missed tasks.

---

## ✨ Features

### 👤 User Management
- 🔐 Secure registration & login with encrypted passwords.

### ✅ Task Management
- ➕ Create, ✏️ Edit, 🗑️ Delete tasks.
- 👀 View all user-specific tasks.
- 📆 Filter tasks by due time (days, hours, or minutes).
- 🔔 Alerts for upcoming or missed tasks.

### ⏱️ Reminder Scheduler
- 📧 Sends automated email reminders:
  - 🗓️ Weekly
  - 📅 Daily
  - ⏰ Hourly
  - ⏳ Final (30 minutes before)
- 🚨 Notification for missed tasks.

### 🛡️ Security
- 🔑 JWT-based authentication.
- 🔒 Configurable access rules with Spring Security.

---

## 🛠️ Technologies Used

- **💻 Core Backend:** Java, Spring Boot, Spring Data JPA  
- **⏰ Scheduler:** Spring Scheduling  
- **🛡️ Security:** Spring Security + JWT  
- **🧪 Testing:** JUnit 5, Mockito  
- **🗄️ Database:** H2 (in-memory) or external DB (configurable)  

---

## 🏗️ Architecture (MVC)

- Model --> Todo, User
  
- Controller --> TodoController, AuthController
  
- Service --> TodoService, NotificationScheduler, UserService
  
- Repository --> TodoRepository, UserRepository

## 🚀 Getting Started

### 🧩 Clone the repository:
```bash
git clone https://github.com/Rangat001/TODO-LIST.git
```

## 📂 Navigate into the project:
```
cd TODO-LIST
```

## ⚙️ Configure database in application.properties

### 🧹 Build the project:
```
mvn clean install
```

### ▶️ Run the app:
```
mvn spring-boot:run
```

## 📡 API Endpoints

### 🔐 Authentication

- POST /public/login – Login user
- POST /public/signup – Register new user

### 📋 Task Management
-POST /todo/create – Create task

-PUT /todo/edit/{id} – Edit task

-GET /todo/getall – View all tasks

-DELETE /todo/delete/{id} – Delete task

### 📨 Notifications

- 📆 Weekly, 📅 Daily, ⏳ Final (30 min prior)
- ⚠️ Missed task notifications

## 🛡️ Security Details

### 🔐 JWT Authentication
-Token issued on login ✅

-Validated on every request for protected endpoints 🔍

### ⚙️ Spring Security
-Stateless session 🔓

-Public/private route segregation 🔧

## 🤝 Contribution
We welcome contributions! 🛠️
To contribute:

- 🍴 Fork the repository

- 🌱 Create a new feature branch

- 💾 Commit your changes

- 🔁 Submit a pull request


