# Journal App - A Spring Boot Complete Application

A full-featured journal and task management application built with **Spring Boot 3.3.12**, featuring user authentication, role-based access control, todo management, weather integration, and email notifications.

---

## 📋 Project Overview

This is a complete end-to-end journaling application that demonstrates modern Spring Boot best practices. It allows users to manage their tasks/todos, view weather information, and receive email notifications—all with robust security and database management.

---

## ✨ Key Features

### 🔐 **Robust Security**
- **Spring Security Integration**: Implements HTTP Basic authentication with BCrypt password encryption
- **Role-Based Access Control (RBAC)**: Three-tier authorization system
  - `USER` role: Access to user and todo management endpoints
  - `ADMIN` role: Administrative privileges
  - Public endpoints for registration and public access
- **Password Encryption**: BCrypt hashing ensures secure password storage
- **CSRF Protection**: Configurable security settings for web applications

### 📝 **User Management**
- User registration with automatic role assignment
- Admin user creation capabilities
- User profile updates with secured password changes
- User deletion with cascading todo cleanup
- Retrieve all users (admin-only functionality)
- Built-in authentication using Spring Security

### ✅ **Todo/Task Management**
- Create, read, update, and delete todos
- User-specific todo isolation (todos are linked to authenticated users)
- Transactional operations ensuring data consistency
- Stream-based filtering for efficient query operations
- Proper HTTP status codes for RESTful responses

### 🌤️ **Weather Integration**
- Real-time weather information retrieval
- City-based weather lookups
- Displays temperature, "feels like" temperature, and wind speed
- Personalized weather greeting for authenticated users
- Seamless integration with WeatherAPI

### 📧 **Email Notification System**
- Send transactional emails to users
- Integration with JavaMail sender
- Error handling with logging
- Supports custom email subjects and body content
- Useful for notifications and user confirmations

### 🗄️ **Database Management**
- **MongoDB Integration**: NoSQL database for flexible schema
- **Spring Data MongoDB**: Simplified data access layer
- **DBRef Relationships**: Proper linking between users and todos
- **Indexed Fields**: Unique username constraints for data integrity
- **Transactional Operations**: @Transactional annotations ensure ACID compliance

---

## 🏗️ Technology Stack

| Layer | Technology |
|-------|-----------|
| **Framework** | Spring Boot 3.3.12 |
| **Language** | Java 17 |
| **Database** | MongoDB |
| **Security** | Spring Security with BCrypt |
| **Build Tool** | Maven 3.8+ |
| **Mail** | Spring Mail (JavaMailSender) |
| **Code Generation** | Lombok (Reduces boilerplate code) |
| **Development** | Spring Boot DevTools (Hot Reload) |
| **Testing** | JUnit 5 with Spring Test |
| **Code Quality** | SonarQube Maven Plugin |

---

## 📦 Project Structure

```
Complete_course_app/
├── src/main/java/net/engineeringdigest/journalApp/
│   ├── model/                          # Data models
│   │   ├── User.java                   # User entity with roles
│   │   ├── Todo.java                   # Todo/task entity
│   │   └── Config.java                 # Application configuration
│   ├── controller/                     # REST API endpoints
│   │   ├── UserController.java         # User CRUD & weather APIs
│   │   ├── TodoController.java         # Todo CRUD operations
│   │   ├── AdminController.java        # Admin-specific endpoints
│   │   └── PublicController.java       # Public/unauthenticated endpoints
│   ├── Service/                        # Business logic layer
│   │   ├── UserService.java            # User operations & weather fetching
│   │   ├── TodoService.java            # Todo operations
│   │   ├── EmailService.java           # Email sending functionality
│   │   ├── WhetherService.java         # Weather API integration
│   │   └── UserDetailsServiceImpl.java  # Spring Security user details
│   ├── Repo/                           # Data access layer
│   │   ├── UserRepo.java               # User repository (Spring Data)
│   │   ├── TodoRepo.java               # Todo repository (Spring Data)
│   │   └── ConfigRepo.java             # Config repository
│   ├── configuration/                  # Spring configurations
│   │   └── SpringSecurity.java         # Security filter chain & auth config
│   ├── apiResponse/                    # API response DTOs
│   │   └── WhetherResponse.java        # Weather API response model
│   └── scheduler/                      # Scheduled tasks
├── src/main/resources/                 # Configuration files
│   ├── application-dev.yml             # Development environment config
│   └── application-production.yml      # Production environment config
└── pom.xml                             # Maven dependencies & build config
```

---

## 🚀 Getting Started

### Prerequisites
- **Java 17** or higher
- **Maven 3.8+** 
- **MongoDB** running locally or accessible via network
- **SMTP Server** (for email functionality)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Complete_course_app
   ```

2. **Configure MongoDB**
   - Update `application-dev.yml` or `application-production.yml` with your MongoDB connection details
   ```yaml
   spring:
     data:
       mongodb:
         uri: mongodb://localhost:27017/journaldb
   ```

3. **Configure Email Settings**
   - Update the mail properties in your active profile configuration
   ```yaml
   spring:
     mail:
       host: smtp.gmail.com
       port: 587
       username: your-email@gmail.com
       password: your-app-password
   ```

4. **Build the project**
   ```bash
   mvn clean install
   ```

5. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The application will be available at `http://localhost:8080`

---

## 📚 API Endpoints

### 🔓 Public Endpoints (`/api/public/**`)
- Publicly accessible without authentication
- User registration endpoint

### 👤 User Endpoints (`/api/User/**`) - Requires USER role
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/User/save` | Register a new user |
| GET | `/api/User/getAllUsers` | Get all users (admin only) |
| PUT | `/api/User/update` | Update user profile |
| DELETE | `/api/User/deleteUser/{username}` | Delete user by username |
| DELETE | `/api/User/deleteAll` | Delete all users |
| GET | `/api/User/weather/{city}` | Get weather for a city |

### ✅ Todo Endpoints (`/api/Todo/**`) - Requires USER role & Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/Todo/save` | Create a new todo |
| GET | `/api/Todo/get/{id}` | Get todo by ID |
| PUT | `/api/Todo/update/{id}` | Update todo |
| DELETE | `/api/Todo/delete/{id}` | Delete todo |

### 🛡️ Admin Endpoints (`/api/Admin/**`) - Requires ADMIN role
- Dedicated admin operations

---

## 🔧 Configuration Profiles

### Development (`dev`)
- **File**: `application-dev.yml`
- Used during development with hot-reload via DevTools
- Enable with: `--spring.profiles.active=dev`

### Production (`production`)
- **File**: `application-production.yml`
- Optimized settings for production deployment
- Enable with: `--spring.profiles.active=production`

---

## 🎯 Architectural Highlights

### **Clean Code Principles**
- **Separation of Concerns**: Controllers, Services, and Repositories are properly separated
- **Dependency Injection**: Constructor-based injection for loose coupling
- **Transactional Integrity**: @Transactional annotations ensure data consistency

### **REST API Best Practices**
- **HTTP Status Codes**: Proper use of 200, 201, 204, 400, 404, 500 codes
- **Request/Response Format**: JSON-based communication
- **RESTful Design**: Resource-oriented endpoints with proper HTTP methods

### **Security Best Practices**
- **Password Hashing**: BCrypt with no plaintext storage
- **Authentication**: Spring Security with HTTP Basic auth
- **Authorization**: Role-based access control (RBAC)
- **CSRF Protection**: Configurable CSRF handling

### **Database Patterns**
- **Spring Data MongoDB**: Automatic repository implementation
- **DBRef Relationships**: Proper object relationships in MongoDB
- **Indexed Queries**: Unique constraints on critical fields
- **Cascading Operations**: Proper cleanup when users are deleted

### **Logging & Monitoring**
- **SLF4J Integration**: Structured logging with @Slf4j Lombok annotation
- **Error Handling**: Comprehensive exception handling with proper logging
- **Code Quality**: SonarQube integration for static code analysis

---

## 🧪 Testing

The project includes Spring Boot Test framework for unit and integration testing.

```bash
mvn test
```

---

## 📦 Build & Deployment

### Build JAR
```bash
mvn clean package
```

This creates an executable JAR in `target/journalApp-0.0.1-SNAPSHOT.jar`

### Run JAR
```bash
java -jar target/journalApp-0.0.1-SNAPSHOT.jar --spring.profiles.active=production
```

---

## 🛠️ Development Features

### **DevTools Support**
- Hot reload for rapid development
- Automatic restart on file changes
- Improves development productivity

### **Lombok Integration**
- Reduces boilerplate code
- @Data, @Slf4j, @NonNull annotations
- Cleaner, more readable code

### **Maven Build Management**
- Spring Boot Maven Plugin for easy JAR creation
- SonarQube integration for code quality analysis
- Proper dependency management with Spring Boot parent POM

---

## 🔄 Data Flow Example: Creating a Todo

1. **User Request**: POST to `/api/Todo/save` with todo data
2. **Authentication**: Spring Security validates user credentials
3. **Controller Layer**: `TodoController.saveToDo()` extracts authenticated username
4. **Service Layer**: `TodoService.saveToDo()` processes the business logic
5. **Repository Layer**: MongoDB saves the todo and updates user reference
6. **Response**: Todo is returned with HTTP 201 (Created) status
7. **Data Consistency**: @Transactional ensures all operations complete or rollback

---

## 🌟 Key Advantages

✅ **Enterprise-Grade Security**: Implements Spring Security best practices  
✅ **Scalable Architecture**: Layered design supports future growth  
✅ **Modern Java**: Built with Java 17 and latest Spring Boot 3.3.12  
✅ **NoSQL Database**: MongoDB provides flexibility and scalability  
✅ **Production-Ready**: Includes configuration profiles, logging, and error handling  
✅ **API-First Design**: RESTful endpoints for easy integration  
✅ **Code Quality**: SonarQube integration for continuous quality monitoring  
✅ **Developer Experience**: DevTools and Lombok for faster development  

---

## 📝 Notes for Developers

- **Authentication**: All endpoints except `/api/public/**` require valid user credentials
- **User Isolation**: Todo operations are scoped to the authenticated user
- **Error Handling**: Comprehensive exception handling with meaningful HTTP status codes
- **Transactions**: Database operations are properly managed with @Transactional
- **Validation**: Input validation through Spring annotations and custom logic
- **Logging**: SLF4J provides detailed operation logging for debugging and monitoring

---

## 📄 License

This project is provided as an educational example of Spring Boot application development.

---

## 🤝 Contributing

This project is designed as a complete learning resource demonstrating:
- Spring Boot application architecture
- Spring Security implementation
- MongoDB integration with Spring Data
- RESTful API design
- Email and weather API integration
- Transaction management and data consistency

---

## 📞 Support

For questions or issues related to this application, refer to the Spring Boot documentation and Spring Security guides.

---

**Built with ❤️ using Spring Boot 3.3.12**

