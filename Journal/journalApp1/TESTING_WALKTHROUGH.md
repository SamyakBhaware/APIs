# Journal App - Visual Testing Walkthrough

## 📸 Step-by-Step Testing with Screenshots Guide

### PART 1: Setup & Run Application

#### Step 1.1: Open Terminal
```bash
cd /home/samyak/Desktop/SPRING\ LESSONS/journalApp1
```

#### Step 1.2: Build the Project
```bash
mvn clean install
```

Expected output:
```
[INFO] BUILD SUCCESS
[INFO] Total time: X.XXXs
[INFO] Finished at: 2026-03-21T...
```

#### Step 1.3: Start the Application
```bash
mvn spring-boot:run
```

Expected output:
```
[INFO] Starting JournalApplication v0.0.1-SNAPSHOT using Java 17.x.x
...
Tomcat started on port(s): 8080 (http)
Started JournalApplication in X.XXX seconds (JVM running for X.XXX)
```

✅ **Server is now running on http://localhost:8080**

---

### PART 2: Testing with Postman (Recommended)

#### Step 2.1: Import Collection

1. Open **Postman**
2. Click: **File** → **Import**
3. Select: `/home/samyak/Desktop/SPRING LESSONS/journalApp1/Journal_App_Postman_Collection.json`
4. Click: **Import**

All endpoints are now pre-configured!

#### Step 2.2: Test Public Endpoint (Health Check)

1. **Collection** → **PUBLIC - User Registration** → **Public Health Check**
2. Click **Send**

```json
Response: 200 OK
"Hello from the public controller!"
```

✅ **Success!**

---

#### Step 2.3: Create First User

1. **PUBLIC - User Registration** → **Create New User**
2. In request body, update:
   ```json
   {
     "username": "alice",
     "email": "alice@example.com",
     "password": "alice123",
     "roles": ["USER"]
   }
   ```
3. Click **Send**

```json
Response: 200 OK
"User created successfully"
```

Check MongoDB Atlas:
- Database: `JournalAppDB`
- Collection: `Users`
- You should see one document with alice's data (password encrypted)

✅ **User created!**

---

#### Step 2.4: Create a Journal Entry

1. **JOURNAL - CRUD Operations** → **Create Journal Entry**
2. **Authorization** tab:
   - Type: **Basic Auth**
   - Username: `alice`
   - Password: `alice123`
   - (Postman auto-adds the header)
3. Request body:
   ```json
   {
     "title": "My First Entry",
     "content": "This is my first journal entry!"
   }
   ```
4. Click **Send**

```json
Response: 200 OK
"Journal created successfully"
```

Check MongoDB:
- Collection: `Journals`
- Should see a new document with alice's journal

✅ **Journal created!**

---

#### Step 2.5: Retrieve All Journals

1. **JOURNAL - CRUD Operations** → **Get All Journals**
2. Auth is already configured (Basic Auth: alice/alice123)
3. Click **Send**

```json
Response: 200 OK
[
  {
    "id": "507f1f77bcf86cd799439011",
    "userId": "alice",
    "title": "My First Entry",
    "content": "This is my first journal entry!",
    "createdAt": "2026-03-21T10:30:00",
    "updatedAt": "2026-03-21T10:30:00"
  }
]
```

**Copy the journal ID from the response** - you'll need it for next steps

✅ **Retrieved all journals!**

---

#### Step 2.6: Update a Journal

1. **JOURNAL - CRUD Operations** → **Update Journal Entry**
2. Replace `JOURNAL_ID_HERE` in URL with the copied ID
   - Example: `http://localhost:8080/journal/507f1f77bcf86cd799439011`
3. Auth: Basic (alice/alice123) - already configured
4. Update request body:
   ```json
   {
     "title": "Updated Title",
     "content": "This is the updated content with more details!"
   }
   ```
5. Click **Send**

```json
Response: 200 OK
"Journal updated successfully"
```

Check MongoDB:
- The `updatedAt` timestamp should be newer now

✅ **Journal updated!**

---

#### Step 2.7: Create Second Journal (for testing)

1. **JOURNAL - CRUD Operations** → **Create Journal Entry**
2. Change body:
   ```json
   {
     "title": "Second Entry",
     "content": "Testing soft delete feature"
   }
   ```
3. Click **Send**

✅ **Created second journal for testing trash**

---

#### Step 2.8: Test Soft Delete (Trash)

1. Get latest journals first:
   - **Get All Journals** → Click **Send**
   - Copy the ID of the second journal

2. **TRASH - Soft Delete Operations** → **Trash Journal (Soft Delete)**
3. Update URL: `...?id=YOUR_JOURNAL_ID_HERE`
4. Click **Send**

Check MongoDB:
- `Journals` collection: Should have 1 journal (the trashed one is gone)
- `Trash` collection: Should have 1 document (the soft-deleted journal)

✅ **Soft delete working!**

---

#### Step 2.9: View Trashed Journals

1. **TRASH - Soft Delete Operations** → **Get Trashed Journals**
2. Auth: alice/alice123
3. Click **Send**

```json
Response: 200 OK
[
  {
    "id": "...",
    "userId": "alice",
    "title": "Second Entry",
    "content": "Testing soft delete feature",
    "createdAt": "...",
    "updatedAt": "..."
  }
]
```

✅ **Retrieved trashed journals!**

---

#### Step 2.10: Restore Journals

1. **TRASH - Soft Delete Operations** → **Restore All Trashed Journals**
2. Auth: alice/alice123
3. Click **Send**

```json
Response: 200 OK
"Journal updated successfully"
```

Check MongoDB:
- `Journals`: Should have 2 journals again
- `Trash`: Should be empty

✅ **Journals restored!**

---

#### Step 2.11: Hard Delete a Journal

1. Get all journals:
   - **Get All Journals** → Click **Send**
   - Copy any journal ID

2. **JOURNAL - CRUD Operations** → **Delete Journal (Hard Delete)**
3. Replace `JOURNAL_ID_HERE` in URL
4. Click **Send**

```json
Response: 200 OK
"Journal deleted successfully"
```

Check MongoDB:
- Journal should be completely gone (not in Trash either)

✅ **Hard delete working!**

---

#### Step 2.12: Get User Profile

1. **USER - User Management** → **Get User Profile**
2. Auth: alice/alice123
3. Click **Send**

```json
Response: 200 OK
{
  "id": "507f1f77bcf86cd799439010",
  "username": "alice",
  "email": "alice@example.com",
  "roles": ["USER"],
  "journals": [
    {
      "id": "...",
      "userId": "alice",
      "title": "My First Entry",
      "content": "...",
      ...
    }
  ]
}
```

You can see all of alice's journals linked via DBRef!

✅ **Retrieved user profile with journals!**

---

#### Step 2.13: Update User Profile

1. **USER - User Management** → **Update User Profile**
2. Auth: alice/alice123
3. Update body:
   ```json
   {
     "username": "alice",
     "email": "newemail@example.com",
     "password": "newpassword123"
   }
   ```
4. Click **Send**

```json
Response: 200 OK
"User updated successfully"
```

Check MongoDB:
- alice's email and password should be updated (password encrypted)

✅ **User updated!**

---

#### Step 2.14: Test Authentication

1. Create a new request: **GET** → `http://localhost:8080/journal/all`
2. **Don't add any authentication**
3. Click **Send**

```json
Response: 401 Unauthorized
```

This proves authentication is working!

✅ **Authentication enforced!**

---

### PART 3: Testing with cURL (Command Line)

#### Step 3.1: Create User via cURL

```bash
curl -X POST http://localhost:8080/public/create \
  -H "Content-Type: application/json" \
  -d '{
    "username": "bob",
    "email": "bob@example.com",
    "password": "bob123",
    "roles": ["USER"]
  }'
```

Response:
```
"User created successfully"
```

---

#### Step 3.2: Create Journal with Auth

```bash
# Generate auth header
AUTH=$(echo -n "bob:bob123" | base64)

curl -X POST http://localhost:8080/journal/create \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic $AUTH" \
  -d '{
    "title": "Bob'\''s Entry",
    "content": "My first entry from cURL"
  }'
```

Response:
```
"Journal created successfully"
```

---

#### Step 3.3: Get Journals with cURL

```bash
AUTH=$(echo -n "bob:bob123" | base64)

curl -X GET http://localhost:8080/journal/all \
  -H "Authorization: Basic $AUTH"
```

Response:
```json
[
  {
    "id": "...",
    "userId": "bob",
    "title": "Bob's Entry",
    ...
  }
]
```

---

### PART 4: Automated Testing with Script

#### Step 4.1: Run Test Script

```bash
chmod +x /home/samyak/Desktop/SPRING\ LESSONS/journalApp1/test_api.sh

/home/samyak/Desktop/SPRING\ LESSONS/journalApp1/test_api.sh
```

This runs all 10 tests automatically:
```
✓ Test 1: CREATE NEW USER
✓ Test 2: PUBLIC HEALTH CHECK
✓ Test 3: CREATE JOURNAL ENTRY
✓ Test 4: GET ALL JOURNALS
✓ Test 5: UPDATE JOURNAL ENTRY
✓ Test 6: TRASH JOURNAL (SOFT DELETE)
✓ Test 7: VIEW TRASHED JOURNALS
✓ Test 8: RESTORE JOURNALS
✓ Test 9: GET USER PROFILE
✓ Test 10: TEST UNAUTHORIZED ACCESS
```

---

### PART 5: Verify in MongoDB Atlas

#### Step 5.1: Open MongoDB Atlas

1. Go to [MongoDB Atlas](https://www.mongodb.com/cloud/atlas)
2. Sign in with your credentials
3. Navigate to **Database** → **Collections**

#### Step 5.2: Check Users Collection

Click: **JournalAppDB** → **Users**

You should see:
```json
{
  "_id": ObjectId("..."),
  "username": "alice",
  "email": "alice@example.com",
  "password": "$2a$10$...(bcrypted)...",
  "roles": ["USER"],
  "journals": [
    {
      "$ref": "Journals",
      "$id": ObjectId("...")
    }
  ]
}
```

✅ **Users Collection verified!**

---

#### Step 5.3: Check Journals Collection

Click: **JournalAppDB** → **Journals**

You should see:
```json
{
  "_id": ObjectId("..."),
  "userId": "alice",
  "title": "My First Entry",
  "content": "This is my first journal entry!",
  "createdAt": ISODate("2026-03-21T10:30:00Z"),
  "updatedAt": ISODate("2026-03-21T10:30:00Z")
}
```

✅ **Journals Collection verified!**

---

#### Step 5.4: Check Trash Collection

Click: **JournalAppDB** → **Trash**

If you soft-deleted journals, you should see them here:
```json
{
  "_id": ObjectId("..."),
  "userId": "alice",
  "title": "Second Entry",
  "content": "Testing soft delete feature",
  ...
}
```

✅ **Trash Collection verified!**

---

### PART 6: Check Application Logs

Look at your terminal where the app is running:

```
[2026-03-21T10:30:00.123+00:00] INFO net.engineeringdigest.journalApp.service.JournalService - Journal saved successfully: Journal(id=..., userId=alice, title=My First Entry, ...)

[2026-03-21T10:31:00.456+00:00] INFO net.engineeringdigest.journalApp.controller.JournalController - ...

[2026-03-21T10:32:00.789+00:00] INFO net.engineeringdigest.journalApp.service.JournalService - Journal deleted by id: ...
```

These logs show:
✅ **Journal operations are being logged**
✅ **Service layer is working**
✅ **Controllers are processing requests**

---

## 🎯 Complete Test Checklist

- [ ] Application starts on port 8080
- [ ] Public endpoint returns greeting message
- [ ] User creation successful (no auth needed)
- [ ] User password is encrypted in MongoDB
- [ ] Journal creation with authentication works
- [ ] Can retrieve all journals for authenticated user
- [ ] Can update journal title and content
- [ ] Can soft delete (trash) journals
- [ ] Trashed journals are in separate collection
- [ ] Can restore trashed journals
- [ ] Can hard delete journals (permanent)
- [ ] User profile shows linked journals
- [ ] Can update user email/password
- [ ] Unauthorized access returns 401/403
- [ ] Each user only sees their own journals
- [ ] Timestamps are auto-set (createdAt, updatedAt)
- [ ] MongoDB collections are created correctly
- [ ] Application logs show all operations

---

## 🐛 Troubleshooting During Testing

### Issue: "Connection refused on port 8080"
**Solution**: Make sure the app is running with `mvn spring-boot:run`

### Issue: "MongoDB connection error"
**Solution**: Check internet connection and IP whitelist in MongoDB Atlas

### Issue: "401 Unauthorized"
**Solution**: Verify Basic Auth header encoding:
```bash
echo -n "username:password" | base64
```

### Issue: "Journal not found"
**Solution**: Copy exact ID from previous request response

### Issue: "User not created"
**Solution**: Check that username/email are unique (no duplicates)

---

## 📊 Summary of What You've Tested

```
✅ User Registration (Public API)
✅ Authentication (HTTP Basic Auth)
✅ Journal Creation (Protected API)
✅ Journal Retrieval (Protected API)
✅ Journal Update (Protected API)
✅ Journal Deletion (Hard Delete)
✅ Journal Soft Delete (Trash)
✅ Trash Management (View & Restore)
✅ User Profile Management
✅ Authorization (Role-based Access)
✅ Data Persistence (MongoDB)
✅ Relationship Management (DBRef)
✅ Timestamp Management (createdAt, updatedAt)
✅ Password Encryption (BCrypt)
✅ Error Handling (Exceptions)
✅ Logging (Audit Trail)
```

---

## 🎉 You're Done!

You've successfully tested all major features of the Journal App:
- REST API endpoints
- User authentication & authorization
- CRUD operations on journals
- Soft & hard delete functionality
- Database operations
- Error handling
- Logging

**Next Steps**:
1. Explore the codebase
2. Add more features (pagination, search, etc.)
3. Write unit tests
4. Deploy to production
5. Monitor logs and performance

---

