# Journal App - Quick Visual Reference

## 🎯 What is This App?

A **Spring Boot REST API** for journaling with user authentication and MongoDB storage.

---

## 📱 Simple API Flow

```
┌─────────────────────────────────────────────────────┐
│           JOURNAL APP USER FLOW                     │
└─────────────────────────────────────────────────────┘

1️⃣  CREATE ACCOUNT
    POST /public/create
    ↓
    User stored in MongoDB

2️⃣  LOGIN (HTTP Basic Auth)
    Authorization: Basic username:password
    ↓
    SpringSecurity validates

3️⃣  CREATE JOURNAL
    POST /journal/create (with auth)
    ↓
    Journal stored with user reference

4️⃣  READ JOURNALS
    GET /journal/all (with auth)
    ↓
    All user's journals returned

5️⃣  UPDATE JOURNAL
    PUT /journal/{id} (with auth)
    ↓
    Journal updated

6️⃣  DELETE OPTIONS
    Hard: DELETE /journal/{id}       → Permanent removal
    Soft: DELETE /journal/trash?id=  → Move to trash

7️⃣  MANAGE TRASH
    GET /journal/trashed              → View trash
    POST /journal/restore             → Restore all
```

---

## 🏗️ Architecture in 30 Seconds

```
CLIENT
  ↓
SPRING SECURITY (checks auth)
  ↓
CONTROLLER (receives request)
  ↓
SERVICE (business logic)
  ↓
REPOSITORY (database queries)
  ↓
MONGODB (stores data)
```

---

## 🔐 How Auth Works

```
Request with credentials:
┌─────────────────────────────────────┐
│ POST /journal/create                │
│ Authorization: Basic am9objpqb2hu...│  ← username:password in Base64
│ {                                   │
│   "title": "My Entry"               │
│ }                                   │
└─────────────────────────────────────┘
           ↓
Spring Security decodes Base64
           ↓
Extracts: username & password
           ↓
Looks up user in MongoDB
           ↓
Compares password with BCrypt hash
           ↓
If ✅ valid → Process request
If ❌ invalid → Return 401 Unauthorized
```

---

## 💾 Data Structure

```
MONGODB
├─ Users Collection
│  ├─ _id
│  ├─ username (unique)
│  ├─ email (unique)
│  ├─ password (encrypted)
│  ├─ roles: ["USER"]
│  └─ journals: [refs...]
│
├─ Journals Collection
│  ├─ _id
│  ├─ userId
│  ├─ title
│  ├─ content
│  ├─ createdAt
│  └─ updatedAt
│
└─ Trash Collection
   ├─ _id
   ├─ userId
   ├─ title
   ├─ content
   ├─ createdAt
   └─ updatedAt
```

---

## 🧪 Testing in 3 Steps

### Option 1: Postman (Easiest)
```
1. Import: Journal_App_Postman_Collection.json
2. Send requests
3. See responses
```

### Option 2: cURL (Quick)
```bash
# Create user
curl -X POST http://localhost:8080/public/create \
  -d '{"username":"john","password":"john123","roles":["USER"]}'

# Get journals (with auth)
curl -X GET http://localhost:8080/journal/all \
  -H "Authorization: Basic am9objpqb2huMTIz"
```

### Option 3: Script (Complete)
```bash
chmod +x test_api.sh
./test_api.sh
```

---

## 📊 API Endpoints Summary

```
PUBLIC (No Auth Required)
├─ POST   /public/create           → Register user
└─ GET    /public                  → Health check

JOURNAL (Requires Auth)
├─ POST   /journal/create          → Create entry
├─ GET    /journal/all             → Get all entries
├─ PUT    /journal/{id}            → Update entry
├─ DELETE /journal/{id}            → Delete permanently
├─ DELETE /journal/trash?id=       → Delete to trash
├─ GET    /journal/trashed         → View trash
└─ POST   /journal/restore         → Restore trash

USER (Requires Auth)
├─ GET    /user/{username}         → Get profile
├─ PUT    /user                    → Update profile
└─ DELETE /user/{username}         → Delete user
```

---

## ⚡ Quick Test Commands

### 1. Start Server
```bash
mvn spring-boot:run
```

### 2. Create User
```bash
curl -X POST http://localhost:8080/public/create \
  -H "Content-Type: application/json" \
  -d '{
    "username": "alice",
    "email": "alice@test.com",
    "password": "alice123",
    "roles": ["USER"]
  }'
```

### 3. Create Journal (with auth)
```bash
AUTH=$(echo -n "alice:alice123" | base64)
curl -X POST http://localhost:8080/journal/create \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic $AUTH" \
  -d '{
    "title": "My First Entry",
    "content": "Hello Journal!"
  }'
```

### 4. Get All Journals
```bash
AUTH=$(echo -n "alice:alice123" | base64)
curl -X GET http://localhost:8080/journal/all \
  -H "Authorization: Basic $AUTH"
```

---

## 🔑 Key Technologies

| Tech | Purpose |
|------|---------|
| Spring Boot | Web framework |
| Spring Security | Authentication & Authorization |
| MongoDB | NoSQL database |
| BCrypt | Password encryption |
| Lombok | Code generation |
| Maven | Build tool |
| Java 17 | Programming language |

---

## 🎯 Success Criteria

✅ You've understood the flow when you can:
1. Explain how authentication works
2. Describe the 3 MongoDB collections
3. List all API endpoints
4. Create a user and journal
5. Update and delete journals
6. Test with Postman or cURL

---

## 📚 Documentation Files

| File | Purpose | Time |
|------|---------|------|
| README.md | Overview | 5 min |
| QUICK_START.md | Fast testing | 5 min |
| PROJECT_FLOW_AND_TESTING.md | Complete guide | 20 min |
| ARCHITECTURE_DETAILED.md | Deep dive | 30 min |
| TESTING_WALKTHROUGH.md | Step-by-step testing | 30 min |

---

## 🚀 Common Workflows

### Workflow 1: Register & Create Entry
```
1. POST /public/create              (Register user)
2. POST /journal/create (auth)      (Create entry)
3. GET /journal/all (auth)          (View entry)
```

### Workflow 2: Soft Delete & Restore
```
1. DELETE /journal/trash?id=        (Move to trash)
2. GET /journal/trashed             (View trash)
3. POST /journal/restore            (Restore)
```

### Workflow 3: Hard Delete
```
1. DELETE /journal/{id}             (Permanent removal)
```

---

## 🔗 Data Relationships

```
USER
  │
  └─── journals (DBRef)
       ├─ Journal 1
       ├─ Journal 2
       └─ Journal 3

When journal is soft-deleted:
  JOURNAL → TRASH (stays linked to user via userId)

When journal is restored:
  TRASH → JOURNAL (back to user's collection)

When journal is hard-deleted:
  JOURNAL → DELETED (no recovery)
```

---

## 💡 Pro Tips

1. **Base64 Auth Header**:
   ```bash
   echo -n "username:password" | base64
   ```

2. **Test in Postman**: Easier than cURL for complex requests

3. **Check MongoDB**: Verify data in Atlas console

4. **Read Logs**: Look at server console for details

5. **Copy IDs**: When updating/deleting, use exact IDs from list

---

## 🐛 Common Issues

| Issue | Fix |
|-------|-----|
| Connection refused | Start server with `mvn spring-boot:run` |
| 401 Unauthorized | Check Base64 auth header encoding |
| Journal not found | Verify journal ID exists & belongs to user |
| MongoDB error | Check internet, IP whitelist in Atlas |

---

## 📞 Getting Help

1. Check: TESTING_WALKTHROUGH.md
2. Check: PROJECT_FLOW_AND_TESTING.md
3. Review: Application logs
4. Verify: Data in MongoDB Atlas
5. Read: Code in `/src/main/java`

---

## 🎓 Learning Path

```
Day 1: Understand the Flow
├─ Read: README.md & QUICK_START.md
└─ Test: Basic create & retrieve

Day 2: Test All Endpoints
├─ Follow: TESTING_WALKTHROUGH.md
├─ Test: All CRUD operations
└─ Verify: Data in MongoDB

Day 3: Deep Dive
├─ Read: ARCHITECTURE_DETAILED.md
├─ Study: Source code
└─ Understand: How it all works together

Day 4+: Extend
├─ Add: New features
├─ Fix: Bugs (if any)
└─ Deploy: To production
```

---

## ✨ What Makes This App Good for Learning

✅ **Complete REST API**: CRUD + authentication  
✅ **Real Database**: MongoDB for data persistence  
✅ **Security**: Spring Security + password encryption  
✅ **Error Handling**: Custom exceptions  
✅ **Logging**: Full audit trail  
✅ **Clean Code**: Service layer pattern  
✅ **Well Documented**: This guide!  

---

## 🎉 You're Ready!

Pick where to start:
- **New?** → QUICK_START.md
- **Want details?** → PROJECT_FLOW_AND_TESTING.md
- **Deep learner?** → ARCHITECTURE_DETAILED.md
- **Just test it?** → Use Postman Collection

**Let's go!** 🚀

