# Sentinel — Login Threat Detection System

Full-stack Java/Spring Boot + HTML/CSS/JS project. Sentinel logs authentication events, detects repeated failed logins, temporarily locks accounts after five consecutive failures, and displays security events in a JWT-protected dashboard.

## Requirements
- JDK 17+
- Maven 3.9+
- Python (to serve the frontend)

## Run backend
```bash
cd sentinel_backend
mvn spring-boot:run
```
API: http://localhost:8080  
H2 console: http://localhost:8080/h2-console  
JDBC URL: `jdbc:h2:file:./data/sentineldb`, user `sa`, blank password.

## Run frontend in another terminal
```bash
cd sentinel_frontend
python -m http.server 5500
```
Open http://127.0.0.1:5500

## Demo
1. Create an account in the UI and sign in.
2. Sign out and attempt several incorrect passwords.
3. Sign in again and review the security event feed.
4. After five consecutive failed attempts, the account is locked for 15 minutes.

## API
- `POST /api/auth/register` — `{ "name":"Demo User", "email":"demo@example.com", "password":"StrongPass123!" }`
- `POST /api/auth/login` — `{ "email":"demo@example.com", "password":"StrongPass123!" }`
- `GET /api/security/summary` — JWT required
- `GET /api/security/events` — JWT required
- `GET /api/security/health` — JWT required

JWT secret in `application.properties` is for local demos only. Set `JWT_SECRET` to a long random secret before deployment. This is an educational demo, not a production security platform.
