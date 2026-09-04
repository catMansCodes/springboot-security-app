# Spring Security Example: JWT Authentication

## Step 1: Create Spring Boot Project

Create a Spring Boot project with the following dependencies:

### Spring Security Dependencies

```xml
spring-boot-starter-web
spring-boot-starter-security
```

### JWT Dependencies

```xml
jjwt-api
jjwt-impl
jjwt-jackson
```

---

## Step 2: Create User Management Layer

Create the components responsible for user authentication.

### Components

```text
User Entity
UserRepository
UserService
UserServiceImpl
```

Responsibilities:

* Create users
* Store encoded passwords
* Load users from database
* Implement UserDetailsService

---

## Step 3: Create JWT Service

Create a `JWTService` class.

Responsibilities:

### Generate JWT Token

```text
Username
Role
Issued Time
Expiration Time
```

↓

```text
JWT Token
```

### Validate JWT Token

Verify:

```text
Signature
Expiration Time
Username
```

### Extract Claims

```text
Username
Role
Expiration
```

from the token.

---

## Step 4: Create JWT Filter

Create:

```java
JWTSecurityFilter
extends OncePerRequestFilter
```

Responsibilities:

1. Read Authorization header
2. Extract Bearer token
3. Validate token
4. Extract claims
5. Create Authentication object
6. Store Authentication in SecurityContextHolder

Flow:

```text
Request
 ↓
Authorization Header
 ↓
JWT Filter
 ↓
Validate Token
 ↓
Extract Claims
 ↓
Create Authentication
 ↓
SecurityContextHolder
```

---

## Step 5: Configure Spring Security

Create `SecurityConfig`.

Responsibilities:

### Configure Public Endpoints

Example:

```text
/api/v1/users/register
/api/v1/auth/login
```

### Configure Protected Endpoints

```text
Any authenticated request
```

### Register JWT Filter

```java
.addFilterBefore(
    jwtSecurityFilter,
    UsernamePasswordAuthenticationFilter.class
)
```

### Disable CSRF

```java
csrf(csrf -> csrf.disable())
```

### Configure Stateless Session

```java
SessionCreationPolicy.STATELESS
```

---

## Step 6: Create Authentication Manager

Configure:

```text
AuthenticationManager
DaoAuthenticationProvider
PasswordEncoder
```

Responsibilities:

```text
Validate Username
Validate Password
Authenticate User
```

---

## Step 7: Create Authentication APIs

### Register User API

```http
POST /api/v1/users/register
```

Responsibilities:

```text
Create User
Encode Password
Save User
```

---

### Login API

```http
POST /api/v1/auth/login
```

Request:

```json
{
  "username": "john",
  "password": "password123"
}
```

Responsibilities:

```text
Authenticate User
Generate JWT Token
Return JWT Token
```

Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

## Step 8: Access Protected APIs

Client sends token in every request.

Header:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

JWT Filter:

```text
Validate Token
 ↓
Extract Claims
 ↓
Authenticate User
 ↓
Allow Request
```

---

## Complete JWT Flow

```text
User Login
 ↓
Username + Password
 ↓
AuthenticationManager
 ↓
UserDetailsService
 ↓
Database Validation
 ↓
JWT Token Generated
 ↓
Token Returned To Client
 ↓
Client Stores Token
 ↓
Client Sends Token In Header
 ↓
JWT Filter
 ↓
Validate Token
 ↓
SecurityContextHolder
 ↓
Controller
```

---

## Request Flow After Login

```text
Client Request
 ↓
Authorization: Bearer Token
 ↓
JWT Filter
 ↓
Token Validation
 ↓
Authentication Object
 ↓
SecurityContextHolder
 ↓
Spring Security
 ↓
Controller
 ↓
Response
```

---

## Key Components

| Component                 | Responsibility                 |
| ------------------------- | ------------------------------ |
| UserServiceImpl           | Load user from database        |
| JWTService                | Generate and validate JWT      |
| JWTSecurityFilter         | Extract and validate token     |
| AuthenticationManager     | Authenticate username/password |
| DaoAuthenticationProvider | Verify credentials             |
| PasswordEncoder           | Encode and verify passwords    |
| SecurityConfig            | Configure Spring Security      |
| SecurityContextHolder     | Store authenticated user       |

---

## Interview Flow

```text
Register User
 ↓
Encode Password
 ↓
Store User
 ↓
Login
 ↓
Authenticate User
 ↓
Generate JWT
 ↓
Return JWT
 ↓
Send JWT In Header
 ↓
Validate JWT
 ↓
Authorize Request
```
