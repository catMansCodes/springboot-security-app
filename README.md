# Spring Security Example: OAuth2 Login with Google

## Step 1: Create OAuth Application in Google Cloud Console

1. Create a new project in **Google Cloud Console**
2. Enable **Google Identity Services / OAuth 2.0**
3. Configure the **OAuth Consent Screen**
4. Create an **OAuth 2.0 Client ID**
5. Configure:

   * Client ID
   * Client Secret
   * Redirect URI (Callback URL)
   * Scopes (Profile, Email, OpenID, etc.)

Example Redirect URI:

```text
http://localhost:8080/login/oauth2/code/google
```

---

## Step 2: Create Spring Boot Project

Create a Spring Boot project with the following dependencies:

```xml
spring-boot-starter-web
spring-boot-starter-security
spring-boot-starter-oauth2-client
```

---

## Step 3: Configure Google OAuth2 Properties

Add the Google OAuth2 configuration in:

```text
application.properties
```

Configure:

* Client ID
* Client Secret
* Scopes
* Authorization Endpoint
* Token Endpoint
* UserInfo Endpoint
* Redirect URI

---

## Step 4: Create Security Configuration

Create a `SecurityConfig` class and enable OAuth2 Login.

Responsibilities:

* Configure URL authorization rules
* Enable OAuth2 Login
* Configure public and secured endpoints
* Define post-login behavior (optional)

Example:

```java
.oauth2Login(Customizer.withDefaults())
```

---

## Step 5: Create Application Endpoints

Create sample REST endpoints:

### Public Endpoint

```text
/api/public
```

Accessible without authentication.

### Secured Endpoint

```text
/api/home
```

Requires Google authentication.

---

## Step 6: Start Application

Run the Spring Boot application.

Spring Security automatically registers the Google OAuth2 login endpoint:

```text
/oauth2/authorization/google
```

---

## Step 7: Login with Google

Open:

```text
http://localhost:8080/oauth2/authorization/google
```

Flow:

```text
User
 ↓
Google Login Page
 ↓
User Authentication
 ↓
Google Consent Screen
 ↓
Authorization Code
 ↓
Access Token
 ↓
User Profile
 ↓
Spring Security Authentication
```

---

## Step 8: Access Authenticated User Information

After successful login, Spring Security creates:

```java
OAuth2AuthenticationToken
```

and stores it in:

```java
SecurityContextHolder
```

User information such as:

```text
Name
Email
Profile Picture
Google User ID
```

can now be accessed within controllers and services.

---

## Complete OAuth2 Flow

```text
User
 ↓
/oauth2/authorization/google
 ↓
Google Authorization Endpoint
 ↓
Authorization Code
 ↓
Google Token Endpoint
 ↓
Access Token
 ↓
Google UserInfo Endpoint
 ↓
User Profile
 ↓
OAuth2AuthenticationToken
 ↓
SecurityContextHolder
 ↓
Authenticated User
```
