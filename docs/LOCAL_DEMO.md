## Prerequisites
- JDK 17, Maven 3.9+, Node 20+
- PostgreSQL 14+ (installer from postgresql.org, includes pgAdmin). Remember the `postgres` password you set.

## 1. Database
In pgAdmin (or `psql -U postgres`) create an empty database:
```sql
CREATE DATABASE stayease_db;
```
Tables are created automatically on first run.

## 2. Backend  (http://localhost:4040)
```bash
cd backend
cp src/main/resources/application-local.properties.example src/main/resources/application-local.properties
# edit it: set your postgres password and a JWT secret
mvn spring-boot:run -Dspring-boot.run.profiles=local
```
Health check: http://localhost:4040/actuator/health -> `{"status":"UP"}`

## 3. Frontend  (http://localhost:7070)
```bash
cd frontend
npm install
npm start
```

## Logins
- **Admin:** email/password from `app.admin.*` (default `admin@stayease.com` / `Admin@12345`), created automatically on first start. Admins land on `/admin`.
- **Customer:** use Register on the site (public sign-up always creates a normal user).
One login page for both; the role in the JWT decides access.

## Demo script (about 3 minutes)
1. Login as admin -> Manage Rooms -> add 3 rooms with photos (Single, Double, Suite).
2. Logout. Register a customer, login.
3. Home -> pick dates + room type -> Search -> open a room -> Book. Note the confirmation code.
4. Try booking the same room for overlapping dates -> rejected (409).
5. Profile -> booking history -> cancel a booking.
6. Find My Booking -> enter the confirmation code.
7. Login as admin -> Manage Bookings / users.

Before the interview: start Postgres, backend and frontend once and pre-load the rooms. Record the flow as a short screen video and link it in the README.
