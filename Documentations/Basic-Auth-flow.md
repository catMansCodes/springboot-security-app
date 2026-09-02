# Spring Security Basic Authentication Flow (Your Example)

When you call:

```http
GET http://localhost:8080/api/v1/home
```

the request follows this flow:

```text
Client (Postman / Browser)
        │
        ▼
Security Filter Chain
        │
        ▼
BasicAuthenticationFilter
        │
        ▼
AuthenticationManager
        │
        ▼
DaoAuthenticationProvider
        │
        ▼
UserDetailsService
(UserServiceImpl)
        │
        ▼
UserRepository
        │
        ▼
Database
        │
        ▼
Authentication Success
        │
        ▼
DispatcherServlet
        │
        ▼
HomeController
        │
        ▼
Response
```

---

## Step 1: Request Arrives

Request:

```http
GET /api/v1/home
Authorization: Basic dmltYWw6MTIzNA==
```

The `Authorization` header contains:

```text
username:password
```

encoded in Base64.

Example:

```text
vimal:1234
```

↓

```text
dmltYWw6MTIzNA==
```

---

## Step 2: SecurityFilterChain Executes

Spring Security intercepts the request before it reaches the controller.

Your configuration:

```java
.authorizeHttpRequests(
    auth -> auth
        .requestMatchers("/api/v1/users/create")
        .permitAll()
        .anyRequest()
        .authenticated()
)
```

Spring checks:

```text
/api/v1/home
```

Is it public?

```text
NO
```

Therefore:

```text
Authentication Required
```

---

## Step 3: BasicAuthenticationFilter

Because of:

```java
.httpBasic(Customizer.withDefaults())
```

Spring activates:

```text
BasicAuthenticationFilter
```

The filter extracts:

```text
Username = vimal
Password = 1234
```

and creates:

```java
new UsernamePasswordAuthenticationToken(
    "vimal",
    "1234"
);
```

---

## Step 4: AuthenticationManager

The filter calls:

```java
authenticationManager.authenticate(authentication);
```

Your bean:

```java
@Bean
public AuthenticationManager authenticationManager(
        UserDetailsService userDetailsService,
        PasswordEncoder passwordEncoder) {

    DaoAuthenticationProvider provider =
            new DaoAuthenticationProvider(userDetailsService);

    provider.setPasswordEncoder(passwordEncoder);

    return new ProviderManager(provider);
}
```

Spring delegates authentication to:

```text
DaoAuthenticationProvider
```

---

## Step 5: DaoAuthenticationProvider

Provider calls:

```java
userDetailsService.loadUserByUsername("vimal");
```

which invokes:

```java
UserServiceImpl.loadUserByUsername(...)
```

---

## Step 6: UserServiceImpl

Method executes:

```java
System.out.println("hi.........");
```

You should see:

```text
hi.........
```

in the console.

Then:

```java
User user = userRepository.findByUserName(userName)
```

runs.

Example database row:

```text
ID : 1
USERNAME : vimal
PASSWORD : $2a$12....
```

---

## Step 7: Build UserDetails

Spring Security User is created:

```java
return User.builder()
        .username(user.getUserName())
        .password(user.getPassword())
        .authorities(Collections.emptyList())
        .build();
```

Result:

```text
Username = vimal
Password = BCrypt Hash
Authorities = []
```

returned to:

```text
DaoAuthenticationProvider
```

---

## Step 8: Password Verification

Provider executes internally:

```java
passwordEncoder.matches(
    rawPassword,
    encodedPassword
);
```

Equivalent:

```java
passwordEncoder.matches(
    "1234",
    "$2a$12...."
);
```

Because you configured:

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
}
```

Spring uses BCrypt comparison.

---

## Step 9: Authentication Success

If password matches:

```text
Authenticated = true
```

Spring creates:

```java
UsernamePasswordAuthenticationToken
```

with:

```text
Principal = vimal
Authenticated = true
```

and stores it in:

```text
SecurityContextHolder
```

---

## Step 10: Request Continues

Now Spring Security allows the request to proceed.

Request reaches:

```java
@GetMapping("/home")
public String home() {
    return "Home sweet Home";
}
```

---

## Step 11: Response Returned

Controller returns:

```text
Home sweet Home
```

Response:

```http
200 OK

Home sweet Home
```

---

# What Happens If Password Is Wrong?

Flow:

```text
BasicAuthenticationFilter
        │
        ▼
AuthenticationManager
        │
        ▼
DaoAuthenticationProvider
        │
        ▼
UserDetailsService
        │
        ▼
PasswordEncoder.matches()
        │
        ▼
FALSE
```

Spring returns:

```http
401 Unauthorized
```

before the controller executes.

Therefore:

```java
@GetMapping("/home")
```

never runs.

---

# Most Important Interview Point

For Basic Authentication:

```text
Client
  ↓
BasicAuthenticationFilter
  ↓
AuthenticationManager
  ↓
DaoAuthenticationProvider
  ↓
UserDetailsService
  ↓
Database
  ↓
PasswordEncoder.matches()
  ↓
SecurityContextHolder
  ↓
Controller
```

Authentication happens **before the controller is called**.
