\# Real Estate Resource and Operations Management System



A full-stack Java application for managing real estate listings, bookings, and customer leads — built with Spring Boot, Spring Security, Spring Data JPA, and Thymeleaf, following a clean layered MVC architecture with role-based access control.



\---



\## Overview



This system models a real-world property management workflow involving three user roles — \*\*Admin\*\*, \*\*Agent\*\*, and \*\*Customer\*\* — each with scoped access to listings, bookings, and lead pipelines. It covers the full lifecycle of a property: from listing, to customer inquiry, to booking, to final sale or rental closure.



\---



\## Tech Stack



| Layer | Technology |

|---|---|

| Language | Java 17 |

| Framework | Spring Boot 3.2.5 |

| Security | Spring Security 6 — BCrypt hashing, session-based, role-based authorization |

| Persistence | Spring Data JPA / Hibernate |

| Database | H2 (in-memory, auto-seeded — zero setup required) |

| View Layer | Thymeleaf, Bootstrap 5, Font Awesome |

| Build Tool | Maven |

| Other | Lombok, Spring Validation |



\---



\## Architecture



A standard layered architecture separates concerns across the request lifecycle:

Controller  →  Service (interface + implementation)  →  Repository  →  Entity



\- \*\*Controllers\*\* handle HTTP requests and delegate to services — no business logic lives here

\- \*\*Services\*\* contain business rules (e.g. booking status cascading to property status)

\- \*\*Repositories\*\* use Spring Data JPA for data access, with derived query methods

\- \*\*DTOs\*\* decouple form/request binding from persistence entities

\- \*\*GlobalExceptionHandler\*\* provides centralized error handling across the app



\### Entity Relationships



\- `User` (1) — (N) `Property` — an agent owns multiple listings

\- `Property` (1) — (N) `Booking`

\- `Property` (1) — (N) `Lead`

\- `User` (1) — (N) `Booking` — as customer

\- `User` (1) — (N) `Lead` — as customer



\---



\## Core Modules



\### Property Management

Full CRUD for property listings (Admin/Agent), with search by title or city. Each property moves through a defined status lifecycle:

AVAILABLE → BOOKED → SOLD / RENTED



\### Booking Management

Customers book available properties. The system automatically locks the property to `BOOKED` on creation, and Agents/Admins can confirm (cascading the property to `SOLD` or `RENTED`), cancel (reverting it to `AVAILABLE`), or mark a booking as completed.



\### Lead \& Inquiry Management

Customers submit inquiries directly from a property listing. Agents track and progress each lead through a sales pipeline:

NEW → CONTACTED → SITE\_VISIT\_SCHEDULED → CLOSED / REJECTED



\### Role-Based Dashboard

Each role sees a tailored dashboard — Admins get system-wide stats (users, properties, bookings, leads), Agents see their own listings and incoming leads, and Customers see their own bookings.



\---



\## Getting Started



\### Prerequisites

\- Java 17+

\- Maven 3.6+



\### Run Locally



```bash

git clone https://github.com/dhanushkr018/real-estate-resource-operations-management-system.git

cd real-estate-resource-operations-management-system

mvn clean install

mvn spring-boot:run

```



The application starts at \*\*http://localhost:8080\*\*. No external database configuration is needed — an in-memory H2 database is auto-seeded with demo users and properties on every startup.



\### H2 Console

For inspecting the live database during a demo:

\- URL: `http://localhost:8080/h2-console`

\- JDBC URL: `jdbc:h2:mem:realestatedb`

\- Username: `sa` — Password: \*(leave blank)\*



\### Demo Credentials



| Role | Email | Password |

|---|---|---|

| Admin | admin@realestate.com | admin123 |

| Agent | agent@realestate.com | agent123 |

| Customer | customer@realestate.com | customer123 |



\---



\## Project Structure

src/main/java/com/realestate



├── config/          → Security configuration, custom UserDetailsService, data seeder



├── controller/       → MVC controllers (Auth, Property, Booking, Lead, Dashboard)



├── dto/              → Request DTOs



├── entity/           → JPA entities and enums



├── repository/       → Spring Data JPA repositories



├── service/          → Service interfaces



│    └── impl/        → Service implementations



└── exception/        → Custom exceptions and global error handler

src/main/resources



├── templates/        → Thymeleaf views



├── static/css/       → Custom styling



└── application.yml   → Application and database configuration



\---



\## Design Decisions



\- \*\*H2 over a production database\*\* — chosen deliberately for this demo to keep setup frictionless; the JPA layer is database-agnostic, so swapping to MySQL/PostgreSQL only requires a connection string change in `application.yml`.

\- \*\*Session-based auth over JWT\*\* — appropriate for a server-rendered Thymeleaf app; a stateless JWT layer is a natural extension if the frontend were decoupled into a SPA.

\- \*\*Status cascading in the service layer, not controllers\*\* — keeps business rules (e.g. a confirmed booking locking a property) testable and independent of the web layer.



\---



\## Future Enhancements



\- JWT-secured REST API to support a decoupled React/Angular frontend

\- Payment gateway integration for booking deposits

\- Document and image upload for property listings and KYC verification

\- Email/SMS notifications on booking and lead status changes

\- Advanced filtering (price range, bedrooms, property type) with pagination



\---



\## Author



\*\*Dhanush K R\*\*

Built as a demonstration project for a Java Full Stack Developer role, showcasing Spring Boot, Spring Security, JPA, and Thymeleaf in a layered MVC architecture.



