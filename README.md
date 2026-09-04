# Spring Security Example - Basic Authentication - `feature/basic-auth`

## Step 1: Create Spring Boot Project

Add the required dependencies:

```xml
spring-boot-starter-web
spring-boot-starter-security
spring-boot-starter-data-jpa
mysql-connector-j
```

---

## Step 2: Create User Management Layer

Create the following components:

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

## Step 3: Implement UserDetailsService

Create `UserServiceImpl` and implement:

```java
UserDetailsService
```

Override:

```java
loadUserByUsername(String username)
```

Responsibilities:

```text
Load user from database
Return UserDetails object
Provide username, password and roles
```

Example Flow:

```text
Username
   ↓
Database Lookup
   ↓
UserDetails
   ↓
Spring Security
```

---

## Step 4: Create Security Configuration

Create:

```java
SecurityConfig
```

Responsibilities:

### Configure Public Endpoints

Example:

```text
/api/v1/users/create
```

### Configure Protected Endpoints

```text
Any authenticated request
```

### Enable Basic Authentication

```java
http.httpBasic(Customizer.withDefaults());
```

### Configure Authorization Rules

```java
requestMatchers(...).permitAll()
.anyRequest().authenticated()
```

---

## Step 5: Configure AuthenticationManager

Create:

```java
AuthenticationManager
```

using:

```java
DaoAuthenticationProvider
```

Responsibilities:

```text
Validate Username
Validate Password
Authenticate User
```

---

## Step 6: Configure Password Encoder

Create:

```java
PasswordEncoder
```

Example:

```java
new BCryptPasswordEncoder(12)
```

Responsibilities:

```text
Encode Password Before Saving
Validate Password During Login
```

---

## Step 7: Create User Registration API

```http
POST /api/v1/users/create
```

Responsibilities:

```text
Accept User Details
Encode Password
Save User
Return User Information
```

---

## Step 8: Access Protected APIs

Request:

```http
GET /api/v1/home
Authorization: Basic base64(username:password)
```

Example:

```text
Authorization: Basic am9objpwYXNzd29yZA==
```

Spring Security Flow:

```text
Request
 ↓
BasicAuthenticationFilter
 ↓
Extract Username & Password
 ↓
AuthenticationManager
 ↓
UserDetailsService
 ↓
Database Validation
 ↓
Authentication Success
 ↓
Controller
```

---

## Complete Basic Auth Flow

```text
Client Request
 ↓
Authorization Header
 ↓
BasicAuthenticationFilter
 ↓
AuthenticationManager
 ↓
DaoAuthenticationProvider
 ↓
UserDetailsService
 ↓
Database Lookup
 ↓
Password Validation
 ↓
Authentication Success
 ↓
Controller
 ↓
Response
```

---

## Important Components

| Component                 | Responsibility              |
| ------------------------- | --------------------------- |
| UserServiceImpl           | Load user from database     |
| UserDetailsService        | User lookup contract        |
| AuthenticationManager     | Authenticate user           |
| DaoAuthenticationProvider | Validate credentials        |
| PasswordEncoder           | Encode and verify passwords |
| SecurityConfig            | Configure Spring Security   |
| BasicAuthenticationFilter | Process Basic Auth header   |
| SecurityContextHolder     | Store authenticated user    |

---

## Interview Flow

```text
Create User
 ↓
Encode Password
 ↓
Store User
 ↓
Client Sends Username & Password
 ↓
Basic Authentication Header
 ↓
Spring Security Authentication
 ↓
Database Validation
 ↓
Access Granted
```

---

## Basic Authentication Request Example

```http
GET /api/v1/home HTTP/1.1
Host: localhost:8080
Authorization: Basic am9objpwYXNzd29yZA==
```

Where:

```text
john:password
        ↓
Base64 Encode
        ↓
am9objpwYXNzd29yZA==
```

> Basic Authentication is stateful by default and sends credentials with every request. For modern REST APIs, JWT Authentication is generally preferred.
