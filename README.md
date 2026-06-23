\# Real Estate Resource and Operations Management System



A full-stack Java web application built with \*\*Spring Boot\*\*, \*\*Spring Security\*\*, \*\*Spring Data JPA\*\*, and \*\*Thymeleaf\*\* to manage real estate properties, bookings, leads, and operations across multiple user roles — Admin, Agent, and Customer.



\---



\## Features



\- \*\*Role-based Access Control\*\* — Admin, Agent, and Customer roles with distinct permissions

\- \*\*Property Management\*\* — Add, edit, delete, search, and list residential/commercial/land properties for sale or rent

\- \*\*Booking Management\*\* — Customers can book available properties; agents/admins confirm, cancel, or complete bookings with automatic property status sync

\- \*\*Lead/Inquiry Management\*\* — Customers send inquiries on properties; agents track and update lead status through the sales pipeline

\- \*\*Role-based Dashboard\*\* — Live stats on properties, bookings, leads, and users

\- \*\*Secure Authentication\*\* — Spring Security with BCrypt password hashing, session-based login

\- \*\*Clean Layered Architecture\*\* — Controller → Service (interface + impl) → Repository → Entity, with DTOs and centralized exception handling



\---



\## Tech Stack



| Layer | Technology |

|---|---|

| Language | Java 17 |

| Backend Framework | Spring Boot 3.2.5 |

| Security | Spring Security 6 (BCrypt, role-based auth) |

| Data Access | Spring Data JPA / Hibernate |

| Database | H2 (in-memory, zero-config) |

| Frontend | Thymeleaf, Bootstrap 5, Font Awesome |

| Build Tool | Maven |

| Other | Lombok, Spring Validation |



\---



\## Architecture
Controller  →  Service (interface + impl)  →  Repository (Spring Data JPA)  →  Entity



↓



DTOs for request binding



↓



GlobalExceptionHandler for centralized error handling

### Core Entities \& Relationships

\- \*\*User\*\* (1) — (N) \*\*Property\*\* \*(agent owns properties)\*

\- \*\*Property\*\* (1) — (N) \*\*Booking\*\*

\- \*\*Property\*\* (1) — (N) \*\*Lead\*\*

\- \*\*User\*\* (1) — (N) \*\*Booking\*\* \*(customer)\*

\- \*\*User\*\* (1) — (N) \*\*Lead\*\* \*(customer)\*



\---



\## ⚙️ Getting Started



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



Application runs at: \*\*http://localhost:8080\*\*



No external database setup required — uses an in-memory H2 database, auto-seeded with demo data on startup.



\### H2 Console (optional)

\- URL: `http://localhost:8080/h2-console`

\- JDBC URL: `jdbc:h2:mem:realestatedb`

\- Username: `sa` / Password: \*(blank)\*



\---



\## Demo Credentials



| Role | Email | Password |

|---|---|---|

| Admin | admin@realestate.com | admin123 |

| Agent | agent@realestate.com | agent123 |

| Customer | customer@realestate.com | customer123 |



\---



\## 📋 Module Overview



\### Property Management

\- CRUD operations for properties (Admin/Agent)

\- Search by title or city

\- Status lifecycle: `AVAILABLE → BOOKED → SOLD / RENTED`



\### Booking Management

\- Customers book available properties

\- Property auto-locks to `BOOKED` on booking creation

\- Agent/Admin confirms (→ `SOLD`/`RENTED`), cancels (→ back to `AVAILABLE`), or completes booking



\### Lead/Inquiry Management

\- Customers submit inquiries on property listings

\- Agents track leads through pipeline: `NEW → CONTACTED → SITE\_VISIT\_SCHEDULED → CLOSED / REJECTED`



\### Dashboard

\- Role-specific live statistics (total properties, bookings, leads, users)



\---



\## Project Structure
src/main/java/com/realestate



├── config/        → Security config, custom UserDetailsService, data seeder



├── controller/     → MVC controllers (Auth, Property, Booking, Lead, Dashboard)



├── dto/            → Request DTOs



├── entity/         → JPA entities and enums



├── repository/     → Spring Data JPA repositories



├── service/        → Service interfaces



│    └── impl/      → Service implementations



└── exception/      → Custom exceptions and global handler

src/main/resources



├── templates/      → Thymeleaf views (Bootstrap-based UI)



├── static/css/     → Custom styling



└── application.yml → App + DB configuration


---



\## Future Enhancements

\- JWT-based REST API for a decoupled SPA frontend (React/Angular)

\- Payment gateway integration for booking deposits

\- Document/image upload (property docs, KYC) with cloud storage

\- Email/SMS notifications for booking and lead status changes

\- Advanced filtering (price range, bedrooms, property type) and pagination



\---



\## Author

Built as a demonstration project for a Java Full Stack Developer role, showcasing Spring Boot, Spring Security, JPA, and Thymeleaf in a layered MVC architecture.

