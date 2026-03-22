# Journal App - Architecture & Flow Visualization

## 🏗️ Complete System Architecture

```
┌─────────────────────────────────────────────────────────────────────────┐
│                          JOURNAL APPLICATION                            │
│                       Spring Boot 3.3.12 REST API                       │
└─────────────────────────────────────────────────────────────────────────┘

                              ┌──────────────┐
                              │   Client     │
                              │ (Browser/    │
                              │  Postman)    │
                              └──────┬───────┘
                                     │
                        ┌────────────┴────────────┐
                        │                         │
                   ┌────▼────┐            ┌──────▼──────┐
                   │ Public  │            │ Protected   │
                   │ Routes  │            │ Routes      │
                   └────┬────┘            └──────┬──────┘
                        │                        │
      ┌─────────────────┴──────────────┐         │
      │                                │         │
  ┌───▼──────┐              ┌──────────▼──────┐  │
  │/public/* │              │ SpringSecurity  │  │
  │No Auth   │              │ Filter Chain    │  │
  └───┬──────┘              │ HTTP Basic Auth │  │
      │                     └──────────┬───────┘  │
      │                                │          │
      │                    ┌───────────▼────────┐ │
      │                    │UserDetailsServiceI │ │
      │                    │  mp (Auth         │ │
      │                    │  Provider)        │ │
      │                    └───────────┬────────┘ │
      │                                │          │
      ▼                                ▼          ▼
  ┌────────────────────────────────────────────────────────┐
  │         CONTROLLER LAYER                               │
  │  ┌────────────┐  ┌───────────────┐  ┌──────────────┐  │
  │  │  Public    │  │ Journal       │  │ User         │  │
  │  │Controller  │  │ Controller    │  │ Controller   │  │
  │  │            │  │               │  │              │  │
  │  │- create    │  │- create       │  │- create      │  │
  │  │  user      │  │- get all      │  │- get         │  │
  │  │- health    │  │- update       │  │- update      │  │
  │  │  check     │  │- delete       │  │- delete      │  │
  │  │            │  │- trash        │  │              │  │
  │  │            │  │- restore      │  │              │  │
  │  └────────────┘  └───────────────┘  └──────────────┘  │
  └────────┬──────────────────┬──────────────────┬─────────┘
           │                  │                  │
           │  Request Routing │                  │
           │                  │                  │
  ┌────────▼──────────────────▼──────────────────▼─────────┐
  │         SERVICE LAYER (Business Logic)                 │
  │  ┌────────────────┐  ┌──────────────────────────────┐  │
  │  │ UserService    │  │ JournalService               │  │
  │  │                │  │                              │  │
  │  │- saveUser()    │  │- saveJournal()               │  │
  │  │- getUser()     │  │- deleteJournalById()         │  │
  │  │- deleteUser()  │  │- showAllJournals()           │  │
  │  │- Password      │  │- showTrashedJournals()       │  │
  │  │  encryption    │  │- updateJournalById()         │  │
  │  │  (BCrypt)      │  │- trashJournalById()          │  │
  │  │                │  │- restoreJournals()           │  │
  │  │                │  │- EmailService (notifications)│  │
  │  └────────────────┘  └──────────────────────────────┘  │
  └────────┬─────────────────────────────────────┬──────────┘
           │                                      │
  ┌────────▼──────────────────────┬───────────────▼────────┐
  │   REPOSITORY LAYER (Data Access)                       │
  │  ┌──────────────┐  ┌────────────────┐  ┌────────────┐  │
  │  │UserRepository│  │JournalRepository  │TrashRepo   │  │
  │  │              │  │                  │ (Soft      │  │
  │  │Methods:      │  │Methods:          │  Delete)   │  │
  │  │- findBy      │  │- findAll()       │            │  │
  │  │  Username()  │  │- findById()      │Methods:    │  │
  │  │- save()      │  │- save()          │- findAll() │  │
  │  │- delete()    │  │- delete()        │- save()    │  │
  │  │              │  │                  │- delete()  │  │
  │  └──────────────┘  └────────────────┘  └────────────┘  │
  └────────┬──────────────────┬──────────────────┬──────────┘
           │                  │                  │
           │    SQL Queries   │ MongoDB Queries  │
           │                  │                  │
  ┌────────▴──────────────────▴──────────────────▴──────────┐
  │                    MONGODB                              │
  │         (Cloud Database - Atlas)                        │
  │                                                          │
  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
  │  │Users         │  │Journals      │  │Trash         │  │
  │  │Collection    │  │Collection    │  │Collection    │  │
  │  │              │  │              │  │              │  │
  │  │- _id         │  │- _id         │  │- _id         │  │
  │  │- username    │  │- userId      │  │- userId      │  │
  │  │- email       │  │- title       │  │- title       │  │
  │  │- password    │  │- content     │  │- content     │  │
  │  │  (bcrypted)  │  │- createdAt   │  │- createdAt   │  │
  │  │- roles       │  │- updatedAt   │  │- updatedAt   │  │
  │  │- journals    │  │              │  │              │  │
  │  │  (DBRef)     │  │              │  │              │  │
  │  └──────────────┘  └──────────────┘  └──────────────┘  │
  │                                                          │
  │  Connection: mongodb+srv://user:pass@cluster.../db     │
  └──────────────────────────────────────────────────────────┘
```

---

## 🔄 Request-Response Flow Diagram

### Flow 1: User Registration (Public)
```
┌─────────────────────────────────────────────────────────────────┐
│ CLIENT REQUEST                                                   │
├─────────────────────────────────────────────────────────────────┤
│ POST /public/create                                              │
│ Content-Type: application/json                                  │
│ {                                                                │
│   "username": "john",                                            │
│   "email": "john@test.com",                                      │
│   "password": "john123",                                         │
│   "roles": ["USER"]                                              │
│ }                                                                │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────────────────────────┐
│ SPRING SECURITY                                                  │
│ - Checks path matches /public/**                                │
│ - Permits without authentication                                │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────────────────────────┐
│ PUBLIC CONTROLLER                                                │
│ - Method: createUser(@RequestBody User user)                   │
│ - Delegates to UserService                                     │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────────────────────────┐
│ USER SERVICE                                                     │
│ - Password encryption: BCrypt.encode("john123")                │
│ - Validates user object                                         │
│ - Calls UserRepository.save()                                  │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────────────────────────┐
│ USER REPOSITORY (MongoDB)                                        │
│ - Inserts document into Users collection                        │
│ - Returns saved User object with _id                            │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────────────────────────┐
│ SERVER RESPONSE                                                  │
├──────────────────────────────────────────────────────────────────┤
│ HTTP Status: 200 OK                                             │
│ Body: "User created successfully"                               │
│                                                                  │
│ MongoDB Document Created:                                        │
│ {                                                                │
│   "_id": ObjectId("5f9e1b9e1b9e1b9e1b9e1b9e"),                │
│   "username": "john",                                            │
│   "email": "john@test.com",                                      │
│   "password": "$2a$10$...(bcrypted)...",                       │
│   "roles": ["USER"],                                             │
│   "journals": []                                                 │
│ }                                                                │
└──────────────────────────────────────────────────────────────────┘
```

---

### Flow 2: Create Journal (Protected)
```
┌─────────────────────────────────────────────────────────────────┐
│ CLIENT REQUEST                                                   │
├─────────────────────────────────────────────────────────────────┤
│ POST /journal/create                                             │
│ Authorization: Basic am9objpqb2huMTIz                            │
│ Content-Type: application/json                                  │
│ {                                                                │
│   "title": "My First Entry",                                    │
│   "content": "Hello, Journal!"                                   │
│ }                                                                │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────────────────────────┐
│ SPRING SECURITY FILTER CHAIN                                    │
│ 1. Extract Base64 header: "am9objpqb2huMTIz"                    │
│ 2. Decode: "john:john123"                                       │
│ 3. Split: username="john", password="john123"                   │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────────────────────────┐
│ USER DETAILS SERVICE IMPL                                        │
│ 1. UserRepository.findByUsername("john")                        │
│ 2. Get user from database                                       │
│ 3. BCrypt.matches("john123", encrypted_password)                │
│ 4. Validate roles [USER]                                        │
│ 5. Return UserDetails or throw AuthenticationException          │
└──────────────────────┬──────────────────────────────────────────┘
                       │
          ┌────────────┴─────────────┐
          │ ✓ Valid User             │
          ▼                           ▼
    ┌─────────────┐            ┌──────────────┐
    │ Continue    │            │ 401/403      │
    │ Request     │            │ Unauthorized │
    └──────┬──────┘            └──────────────┘
           │
           ▼
┌──────────────────────────────────────────────────────────────────┐
│ JOURNAL CONTROLLER                                               │
│ - Extract authentication: SecurityContextHolder.getContext()    │
│ - Get username: auth.getName() → "john"                         │
│ - Create Journal object                                          │
│ - Set createdAt timestamp: LocalDateTime.now()                  │
│ - Call JournalService.saveJournal()                             │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────────────────────────┐
│ JOURNAL SERVICE                                                  │
│ - Validate journal (not null)                                   │
│ - Log action                                                     │
│ - Call JournalRepository.save()                                 │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────────────────────────┐
│ JOURNAL REPOSITORY (MongoDB)                                     │
│ - Insert document into Journals collection                      │
│ - Auto-generate _id (ObjectId)                                  │
│ - Return saved Journal with _id                                 │
└──────────────────────┬──────────────────────────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────────────────────────┐
│ SERVER RESPONSE                                                  │
├──────────────────────────────────────────────────────────────────┤
│ HTTP Status: 200 OK                                             │
│ Body: "Journal created successfully"                            │
│                                                                  │
│ MongoDB Document Created:                                        │
│ {                                                                │
│   "_id": ObjectId("5f9f1b9e1b9e1b9e1b9e1b9f"),                │
│   "userId": "john",                                              │
│   "title": "My First Entry",                                    │
│   "content": "Hello, Journal!",                                 │
│   "createdAt": ISODate("2026-03-21T10:30:00Z"),                │
│   "updatedAt": ISODate("2026-03-21T10:30:00Z")                 │
│ }                                                                │
└──────────────────────────────────────────────────────────────────┘
```

---

## 🔐 Security Layer Details

```
┌────────────────────────────────────────────────────────────────┐
│          SPRING SECURITY CONFIGURATION                         │
│                                                                 │
│  /public/** → permitAll()                                      │
│      ├─ /public/create → User Registration                     │
│      └─ /public          → Health Check                        │
│                                                                 │
│  /journal/** → hasAnyRole("USER", "ADMIN")                     │
│      ├─ /journal/create                                        │
│      ├─ /journal/all                                           │
│      ├─ /journal/{id}  (PUT/DELETE)                            │
│      ├─ /journal/trash                                         │
│      ├─ /journal/trashed                                       │
│      └─ /journal/restore                                       │
│                                                                 │
│  /user/** → authenticated()                                    │
│      ├─ GET /user/{username}                                   │
│      ├─ PUT /user                                              │
│      └─ DELETE /user/{username}                                │
│                                                                 │
│  anyRequest() → authenticated()                                │
│      └─ Everything else requires authentication               │
│                                                                 │
│  HTTP Basic Auth:                                              │
│      - Authorization: Basic <base64(username:password)>        │
│      - Password verification: BCryptPasswordEncoder            │
│      - Enabled: .httpBasic(Customizer.withDefaults())          │
│                                                                 │
│  CSRF:                                                         │
│      - Disabled for API development                            │
│      - .csrf(AbstractHttpConfigurer::disable)                  │
└────────────────────────────────────────────────────────────────┘
```

---

## 📊 Data Relationship Diagram

```
┌─────────────────────┐
│      USER           │
├─────────────────────┤
│ _id: ObjectId       │
│ username: String    │
│ email: String       │
│ password: String    │
│ roles: [String]     │
│ journals: [DBRef]◄──┼─────┐
└─────────────────────┘     │
                            │
                   One-to-Many
                     DBRef Link
                            │
                            │
                    ┌───────▼──────────┐
                    │   JOURNAL        │
                    ├──────────────────┤
                    │ _id: ObjectId    │
                    │ userId: String───┼─────┐
                    │ title: String    │     │
                    │ content: String  │     │
                    │ createdAt: Date  │     │
                    │ updatedAt: Date  │     │
                    └──────────────────┘     │
                                            │
                                      Points Back
                                        To User
```

---

## 🔄 Life Cycle of a Journal Entry

```
CREATE
  │
  ▼
┌─────────────────────────────────┐
│ ACTIVE JOURNAL                  │
│ Location: Journals Collection   │
│ - Visible in /journal/all       │
│ - Can be updated                │
│ - Can be trashed                │
└─────────┬───────────────────────┘
          │
          ├─────────────────┬──────────────────┐
          │                 │                  │
    ┌─────▼──────┐   ┌──────▼──────┐   ┌──────▼──────┐
    │ DELETE      │   │ TRASH       │   │ UPDATE      │
    │ HARD DELETE │   │ SOFT DELETE │   │ MODIFY      │
    └─────┬──────┘   └──────┬──────┘   └─────────────┘
          │                 │
          │         ┌───────▼──────────┐
          │         │ TRASHED JOURNAL  │
          │         │ Location: Trash  │
          │         │ Collection       │
          │         │ - Not visible    │
          │         │ - Can restore    │
          │         └───────┬──────────┘
          │                 │
          │         ┌───────▼──────────┐
          │         │ RESTORE          │
          │         │ Move back to     │
          │         │ Journals Coll.   │
          │         └───────┬──────────┘
          │                 │
          │                 ▼
          │         ┌──────────────────┐
          │         │ ACTIVE AGAIN     │
          │         └──────────────────┘
          │
    ┌─────▼──────────────┐
    │ PERMANENTLY DELETED │
    │ No recovery        │
    └────────────────────┘
```

---

## 🧪 Testing Each Layer

```
┌──────────────────────────────────────────────────────────────┐
│  UNIT TEST (Service Layer)                                  │
│  - Test business logic without HTTP/DB                      │
│  - Mock repositories                                        │
│  - Example: JournalService.saveJournal()                    │
│                                                              │
│  @Test                                                       │
│  void testSaveJournal() {                                   │
│    Journal j = new Journal();                               │
│    service.saveJournal(j);                                  │
│    verify(repository).save(j);                              │
│  }                                                           │
└──────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────┐
│  INTEGRATION TEST (Controller + Service + Repo)             │
│  - Test actual HTTP requests                                │
│  - Use TestRestTemplate                                     │
│  - Mock or use embedded MongoDB                             │
│  - Example: POST /journal/create                            │
│                                                              │
│  @SpringBootTest(webEnvironment = RANDOM_PORT)             │
│  class JournalControllerTests {                             │
│    @Autowired TestRestTemplate template;                    │
│                                                              │
│    @Test                                                     │
│    void testCreateJournal() {                               │
│      var response = template.postForEntity(                 │
│        "/journal/create", journal, String.class            │
│      );                                                      │
│      assert response.getStatusCode() == OK;                │
│    }                                                         │
│  }                                                           │
└──────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────┐
│  END-TO-END TEST (Full Application)                         │
│  - Test from client perspective                             │
│  - Manual or Postman/cURL                                   │
│  - Against live MongoDB                                     │
│  - Sequence: Register → Create → Read → Update → Delete    │
└──────────────────────────────────────────────────────────────┘
```

---

## 📈 Performance & Scalability Considerations

```
Current Architecture:
├─ MongoDB (NoSQL)
│  ├─ Pros: Flexible schema, easy to scale
│  ├─ Cons: No ACID transactions (partially fixed in 4.0+)
│  └─ Indexing: username, email
│
├─ Spring Security (HTTP Basic)
│  ├─ Pros: Simple, easy to implement
│  ├─ Cons: Credentials sent per request, not best for prod
│  └─ Improvement: Use JWT tokens instead
│
├─ DBRef Relationships
│  ├─ Pros: Maintains referential integrity
│  ├─ Cons: Requires additional DB calls
│  └─ Improvement: Consider embedding for frequently accessed data
│
└─ Single Service Instance
   ├─ Limitation: No load balancing
   └─ Improvement: Deploy multiple instances + reverse proxy
```

---

## 🚀 Future Enhancements

```
1. API Versioning
   - /api/v1/journal/create
   - Allows for backward compatibility

2. JWT Authentication
   - Replace HTTP Basic Auth
   - Better security, no credentials per request

3. Rate Limiting
   - Prevent brute force attacks
   - Use Spring Cloud Gateway

4. Caching
   - Cache user journals
   - Redis integration

5. Full-Text Search
   - Search journals by content
   - MongoDB text indexes

6. Real-time Notifications
   - WebSocket integration
   - Push notifications

7. Pagination
   - /journal/all?page=0&size=10
   - Better performance with large datasets

8. File Attachments
   - Store journal files
   - AWS S3 integration

9. Social Features
   - Share journals
   - Collaboration
   - Comments

10. Analytics
    - Track user activity
    - Generate insights
```

---

## 📝 Summary

This Journal App demonstrates:
- ✅ **REST API Design**: CRUD operations on journals
- ✅ **Spring Security**: Authentication & authorization
- ✅ **MongoDB Integration**: Document database operations
- ✅ **Service Layer Pattern**: Separation of concerns
- ✅ **Repository Pattern**: Data access abstraction
- ✅ **Transaction Management**: @Transactional annotations
- ✅ **Exception Handling**: Custom exceptions for error management
- ✅ **Logging**: Track application behavior

---

