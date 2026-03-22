# Journal App - Complete Overview & Testing Guide

## 📚 Documentation Index

This project now has comprehensive documentation. Here's what each file contains:

| File | Purpose |
|------|---------|
| **QUICK_START.md** | 5-minute quick start guide with cURL examples |
| **PROJECT_FLOW_AND_TESTING.md** | Complete architecture, flow diagrams, and detailed testing guide |
| **ARCHITECTURE_DETAILED.md** | In-depth system architecture with visual diagrams |
| **TESTING_WALKTHROUGH.md** | Step-by-step testing with Postman/cURL/Script |
| **Journal_App_Postman_Collection.json** | Ready-to-import Postman API collection |
| **test_api.sh** | Automated bash script for complete API testing |

---

## 🎯 What is This Project?

**Journal App** is a Spring Boot REST API application that allows users to:
- ✅ Create an account (registration)
- ✅ Authenticate with username/password
- ✅ Write journal entries
- ✅ Read all their journals
- ✅ Update existing entries
- ✅ Delete entries (hard delete)
- ✅ Soft delete entries (trash & restore)
- ✅ Manage user profile

---

## 🏗️ Technology Stack

```
Backend:           Spring Boot 3.3.12
Language:          Java 17
Database:          MongoDB (Cloud - Atlas)
Authentication:    HTTP Basic Auth + Spring Security
Encryption:        BCrypt (passwords)
Build Tool:        Maven
Additional:        Lombok, Email Service
```

---

## 🌊 High-Level Flow

```
CLIENT REQUEST
    ↓
SPRING SECURITY (Authentication & Authorization)
    ↓
CONTROLLER (Parse request, extract auth)
    ↓
SERVICE (Business logic)
    ↓
REPOSITORY (Database operations)
    ↓
MONGODB (Persistent storage)
    ↓
RESPONSE BACK TO CLIENT
```

---

## 🚀 Quick Start (3 Steps)

### Step 1: Start the Application
```bash
cd /home/samyak/Desktop/SPRING\ LESSONS/journalApp1
mvn spring-boot:run
```

Server runs on: **http://localhost:8080**

### Step 2: Create a User (No Auth Required)
```bash
curl -X POST http://localhost:8080/public/create \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john",
    "email": "john@example.com",
    "password": "john123",
    "roles": ["USER"]
  }'
```

### Step 3: Create a Journal (With Auth)
```bash
curl -X POST http://localhost:8080/journal/create \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic $(echo -n 'john:john123' | base64)" \
  -d '{
    "title": "My First Entry",
    "content": "Hello Journal!"
  }'
```

✅ **Done! You've tested the core flow**

---

## 📋 Complete API Endpoints

### Public Endpoints (No Authentication)
```
POST   /public/create       → Register new user
GET    /public              → Health check
```

### Journal Endpoints (Requires Authentication)
```
POST   /journal/create      → Create new journal entry
GET    /journal/all         → Get all user's journals
PUT    /journal/{id}        → Update journal entry
DELETE /journal/{id}        → Delete journal (permanent)
DELETE /journal/trash?id={id} → Move journal to trash (soft delete)
GET    /journal/trashed     → View all trashed journals
POST   /journal/restore     → Restore all trashed journals
```

### User Endpoints (Requires Authentication)
```
GET    /user/{username}     → Get user profile
PUT    /user                → Update user profile
DELETE /user/{username}     → Delete user account
```

---

## 🔐 Authentication Details

**Type**: HTTP Basic Authentication

**How it works**:
1. Client sends: `Authorization: Basic base64(username:password)`
2. Server extracts username & password
3. Server validates against MongoDB (bcrypt password comparison)
4. If valid, request proceeds
5. If invalid, returns 401 Unauthorized

**Example**:
```
Username: john
Password: john123
Base64:   echo -n "john:john123" | base64 → am9objpqb2huMTIz
Header:   Authorization: Basic am9objpqb2huMTIz
```

---

## 💾 Database Structure

### Collections in MongoDB

**Users**:
- Store user account information
- Username and email are unique (indexed)
- Password is encrypted with BCrypt
- Contains DBRef links to user's journals
- Roles for authorization (USER, ADMIN)

**Journals**:
- Store journal entries
- Linked to user via userId field
- Timestamps for creation & updates
- Active journals (not deleted)

**Trash**:
- Store soft-deleted journals
- Same structure as Journals
- Can be restored to Journals collection
- Allows recovery of accidentally deleted entries

---

## 🔄 Key Workflows

### Workflow 1: User Registration & First Journal Entry

```
1. Register User
   POST /public/create
   ↓
   User stored in MongoDB (password encrypted)

2. Authenticate
   HTTP Basic Auth with username:password
   ↓
   Spring Security validates credentials

3. Create Journal
   POST /journal/create (with auth header)
   ↓
   Journal stored with userId = authenticated username

4. Retrieve Journals
   GET /journal/all (with auth header)
   ↓
   Returns only this user's journals
```

### Workflow 2: Soft Delete & Restore

```
1. Soft Delete Journal
   DELETE /journal/trash?id={id}
   ↓
   Journal moved from Journals to Trash collection

2. View Trashed
   GET /journal/trashed
   ↓
   Shows all user's trashed journals

3. Restore
   POST /journal/restore
   ↓
   All trashed journals moved back to Journals
```

### Workflow 3: Hard Delete (Permanent)

```
1. Hard Delete Journal
   DELETE /journal/{id}
   ↓
   Journal completely removed (no recovery possible)
```

---

## 🧪 Testing Methods (Choose One)

### Method 1: Postman (Easiest)
1. Import `Journal_App_Postman_Collection.json`
2. Set Basic Auth in requests
3. Click "Send" for each endpoint
4. See formatted responses
- **Best for**: GUI-based testing, exploration

### Method 2: cURL (Command Line)
```bash
curl -X POST http://localhost:8080/journal/create \
  -H "Authorization: Basic $(echo -n 'john:john123' | base64)" \
  -H "Content-Type: application/json" \
  -d '{"title":"Entry","content":"Content"}'
```
- **Best for**: Scripts, CI/CD pipelines, quick testing

### Method 3: Automated Script
```bash
chmod +x test_api.sh
./test_api.sh
```
- **Best for**: Complete test coverage, batch testing

### Method 4: Unit Tests (Coming Soon)
- Test service layer with mocked repositories
- Test controller with TestRestTemplate
- Test security configurations

---

## 📊 Example: Complete User Journey

### User: Alice

**Step 1: Register**
```
POST /public/create
{
  "username": "alice",
  "email": "alice@example.com",
  "password": "alice123",
  "roles": ["USER"]
}
✓ Response: "User created successfully"
✓ MongoDB: alice record created
```

**Step 2: Authenticate & Create Journal**
```
POST /journal/create
Auth: alice:alice123
{
  "title": "Day 1: My Journey",
  "content": "Starting my journal today..."
}
✓ Response: "Journal created successfully"
✓ MongoDB: journal added with userId=alice
```

**Step 3: View All Journals**
```
GET /journal/all
Auth: alice:alice123
✓ Response: [journal1, journal2, ...]
✓ Only alice's journals returned
```

**Step 4: Update Entry**
```
PUT /journal/{id}
Auth: alice:alice123
{
  "title": "Day 1: Updated Title",
  "content": "Updated content..."
}
✓ Response: "Journal updated successfully"
✓ MongoDB: updatedAt timestamp changed
```

**Step 5: Soft Delete**
```
DELETE /journal/trash?id={id}
Auth: alice:alice123
✓ Response: void (success)
✓ MongoDB: journal moved from Journals to Trash
```

**Step 6: View Trash**
```
GET /journal/trashed
Auth: alice:alice123
✓ Response: [deleted_journal]
✓ Only alice's trashed journals returned
```

**Step 7: Restore**
```
POST /journal/restore
Auth: alice:alice123
✓ Response: "Journal updated successfully"
✓ MongoDB: journal moved from Trash back to Journals
```

**Step 8: Hard Delete**
```
DELETE /journal/{id}
Auth: alice:alice123
✓ Response: "Journal deleted successfully"
✓ MongoDB: journal completely removed
```

---

## 🔍 Code Organization

```
src/main/java/net/engineeringdigest/journalApp/
├── JournalApplication.java              # Main entry point
│
├── Configuration/
│   └── SpringSecurity.java              # Security config
│                                         # - Auth rules
│                                         # - Password encoder
│
├── controller/
│   ├── PublicController.java            # Public endpoints
│   ├── JournalController.java           # Journal CRUD
│   └── UserController.java              # User management
│
├── entity/
│   ├── Journal.java                     # Journal document
│   └── User.java                        # User document
│
├── repository/
│   ├── JournalRepository.java           # DB queries
│   ├── UserRepository.java              # DB queries
│   └── TrashRepo.java                   # Soft delete tracking
│
├── service/
│   ├── JournalService.java              # Business logic
│   ├── UserService.java                 # User logic
│   ├── UserDetailsServiceImpl.java       # Auth provider
│   └── EmailService.java                # Email notifications
│
└── Exceptions/
    ├── ResourceNotFoundException.java
    ├── DataSaveException.java
    ├── DataFetchException.java
    ├── DataUpdateException.java
    └── DataDeleteException.java
```

---

## 🎓 Learning Outcomes

By exploring this project, you'll learn:

✅ **Spring Boot**: How to build REST APIs
✅ **Spring Security**: Authentication & authorization
✅ **MongoDB**: NoSQL database operations
✅ **REST Architecture**: Request/response patterns
✅ **Service Layer**: Business logic separation
✅ **Repository Pattern**: Data access abstraction
✅ **Exception Handling**: Custom exceptions
✅ **Logging**: Application monitoring
✅ **Transactions**: @Transactional management
✅ **DTOs**: Data transfer objects (entities)

---

## 🚨 Important Notes

1. **Credentials in Code**: Email/MongoDB connection in `application-dev.yml`
   - Should be in environment variables for production
   - Never commit secrets to version control

2. **HTTP Basic Auth**: Good for development, not ideal for production
   - Production should use JWT tokens
   - Or OAuth2 / OpenID Connect

3. **CSRF Disabled**: For API development
   - Should be enabled for web applications

4. **No HTTPS**: Local development only
   - Always use HTTPS in production

5. **MongoDB Cloud**: Requires internet connection
   - Can use local MongoDB for development
   - Update connection string in properties file

---

## 📈 Performance Tips

- **Add indexes**: Already done for username/email
- **Use pagination**: For large journal lists
- **Cache results**: Use Redis for frequently accessed data
- **Batch operations**: For bulk updates
- **Monitor logs**: Check for slow queries

---

## 🛠️ Troubleshooting

| Problem | Solution |
|---------|----------|
| App won't start | Check Java 17 installed, port 8080 free |
| MongoDB error | Check internet, IP whitelist in Atlas |
| Auth fails | Verify Base64 encoding, user exists |
| Journal not found | Check ID is correct, belongs to user |
| Outdated dependencies | Run `mvn clean install` |

---

## 📚 Recommended Reading

1. **Spring Security**: https://spring.io/projects/spring-security
2. **Spring Data MongoDB**: https://spring.io/projects/spring-data-mongodb
3. **RESTful API Design**: https://restfulapi.net/
4. **MongoDB Documentation**: https://docs.mongodb.com/

---

## 🎉 Next Steps

1. ✅ **Understand the flow** - Read PROJECT_FLOW_AND_TESTING.md
2. ✅ **Test the app** - Follow QUICK_START.md or TESTING_WALKTHROUGH.md
3. ✅ **Explore code** - Read the Java source files
4. ✅ **Add features** - Enhance with pagination, search, etc.
5. ✅ **Write tests** - Add unit and integration tests
6. ✅ **Deploy** - Deploy to cloud (AWS, Heroku, etc.)

---

## 📞 Files Created for You

All documentation files are in: `/home/samyak/Desktop/SPRING LESSONS/journalApp1/`

```
├── README.md                                    (This file)
├── QUICK_START.md                              (5-min quick start)
├── PROJECT_FLOW_AND_TESTING.md                 (Complete guide)
├── ARCHITECTURE_DETAILED.md                    (Deep dive)
├── TESTING_WALKTHROUGH.md                      (Step-by-step testing)
├── Journal_App_Postman_Collection.json         (Postman collection)
└── test_api.sh                                 (Automated test script)
```

---

## ✅ Verification Checklist

Before you move on, verify:

- [ ] Application runs on localhost:8080
- [ ] Can create users via `/public/create`
- [ ] Can authenticate with HTTP Basic Auth
- [ ] Can create journals via `/journal/create`
- [ ] Can retrieve journals via `/journal/all`
- [ ] Can update journals via `/journal/{id}`
- [ ] Can delete journals (hard & soft)
- [ ] Can view trashed journals
- [ ] Can restore journals
- [ ] Data persists in MongoDB
- [ ] Unauthorized access is blocked
- [ ] Logging shows all operations

---

## 🎯 Success Criteria

Your testing is complete when you've:
1. Started the application successfully
2. Created at least one user
3. Created at least one journal entry
4. Retrieved the journal entry
5. Updated it
6. Soft deleted it
7. Restored it
8. Hard deleted it
9. Verified data in MongoDB
10. Tested unauthorized access returns error

---

## 💡 Quick Reference

### Base URL
```
http://localhost:8080
```

### Test User Credentials
```
Username: testuser
Password: password123
```

### Create User Command
```bash
curl -X POST http://localhost:8080/public/create \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@test.com","password":"password123","roles":["USER"]}'
```

### Create Journal Command
```bash
AUTH=$(echo -n "testuser:password123" | base64)
curl -X POST http://localhost:8080/journal/create \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic $AUTH" \
  -d '{"title":"My Entry","content":"Content here"}'
```

---

## 🎉 You're Ready!

All the tools and documentation you need are set up. Start with **QUICK_START.md** for a 5-minute overview, then move to **TESTING_WALKTHROUGH.md** for detailed testing steps.

**Happy coding!** 🚀

