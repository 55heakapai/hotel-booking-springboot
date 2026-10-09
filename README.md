# StayEase – Hotel Management & Booking Platform

Full-stack hotel booking platform: guests search rooms by date and type, book instantly and manage reservations; admins manage rooms, bookings and users.

**Tech:** Java 17 · Spring Boot 3 · Spring Security · JWT · Hibernate/JPA · PostgreSQL · React 18

## Features
- JWT authentication (stateless) with role-based access: `USER` and `ADMIN`
- Secure by default: BCrypt passwords, public registration always creates `USER`, ownership checks so users can only see/cancel their own bookings
- Room management (admin): add / edit / delete rooms with photo upload
- Search available rooms by check-in, check-out and room type
- Booking with server-side date-overlap validation (HTTP 409 on conflict), confirmation code, lookup by code, cancellation
- Admin dashboard: manage rooms, bookings and users
- Config through environment variables (12-factor), health endpoint `/actuator/health`

## Architecture
```
Browser ──▶ React app ──▶ Spring Boot API (JWT, RBAC) ──▶ PostgreSQL
                                   └──▶ local disk (/uploads, room photos)
```

## Project structure
```
stayease/
├── backend/            Spring Boot API (controller / service / repo / entity / security)
├── frontend/           React app
└── docs/               API reference and local demo guide
```

## Run locally
Prerequisites: JDK 17, Maven, Node 20, PostgreSQL 14+. Full steps in [docs/LOCAL_DEMO.md](docs/LOCAL_DEMO.md).
```bash
# backend (http://localhost:4040)
cd backend && mvn spring-boot:run -Dspring-boot.run.profiles=local
# frontend (http://localhost:7070)
cd frontend && npm install && npm start
```
A first admin is created on startup from `app.admin.email` / `app.admin.password`. Room photos are stored in `backend/uploads`.

## Demo
Step-by-step local demo and logins: [docs/LOCAL_DEMO.md](docs/LOCAL_DEMO.md)

## API overview
See [docs/API.md](docs/API.md).

## Tests
```bash
cd backend && mvn test
```