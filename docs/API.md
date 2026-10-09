# API reference
Base URL: `http://<host>:4040` (or `/api` behind Nginx). Responses use `{ statusCode, message, ... }`.
Protected endpoints need `Authorization: Bearer <token>`.

| Method | Endpoint | Access | Purpose |
|---|---|---|---|
| POST | /auth/register | public | Register (always role USER) |
| POST | /auth/login | public | Login, returns JWT + role |
| GET | /rooms/all | public | List rooms |
| GET | /rooms/types | public | Distinct room types |
| GET | /rooms/room-by-id/{id} | public | Room details |
| GET | /rooms/available-rooms-by-date-and-type | public | `checkInDate`, `checkOutDate`, `roomType` |
| POST | /rooms/add | ADMIN | Add room (multipart: photo, roomType, roomPrice, roomDescription) |
| PUT | /rooms/update/{id} | ADMIN | Update room |
| DELETE | /rooms/delete/{id} | ADMIN | Delete room |
| POST | /bookings/book-room/{roomId}/{userId} | owner / ADMIN | Create booking (409 if dates overlap) |
| GET | /bookings/all | ADMIN | All bookings |
| GET | /bookings/get-by-confirmation-code/{code} | public | Find booking |
| DELETE | /bookings/cancel/{bookingId} | owner / ADMIN | Cancel booking |
| GET | /users/all | ADMIN | All users |
| GET | /users/get-logged-in-profile-info | authenticated | Own profile |
| GET | /users/get-by-id/{id} | owner / ADMIN | User by id |
| GET | /users/get-user-bookings/{id} | owner / ADMIN | Booking history |
| DELETE | /users/delete/{id} | ADMIN | Delete user |
| GET | /actuator/health | public | Health check |
