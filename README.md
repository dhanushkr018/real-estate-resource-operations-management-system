Real Estate Resource and Operations Management System

A full-stack Java application for managing real estate listings, bookings, and customer leads — built with Spring Boot, Spring Security, Spring Data JPA, and Thymeleaf, following a clean layered MVC architecture with role-based access control.

------------------------------------------------------------

OVERVIEW

This system models a real-world property management workflow involving three user roles — Admin, Agent, and Customer — each with scoped access to listings, bookings, and lead pipelines.

It covers the complete lifecycle of a property:

Listing → Inquiry → Booking → Closure (Sale/Rent)

------------------------------------------------------------

TECH STACK

Language: Java 17
Framework: Spring Boot 3.2.5
Security: Spring Security 6 (BCrypt, session-based, role-based authorization)
Persistence: Spring Data JPA / Hibernate
Database: H2 In-Memory Database (auto-seeded, zero setup required)
View Layer: Thymeleaf, Bootstrap 5, Font Awesome
Build Tool: Maven
Utilities: Lombok, Spring Validation

------------------------------------------------------------

ARCHITECTURE

Controller → Service (Interface + Implementation) → Repository → Entity

Layer Responsibilities:
- Controller: Handles HTTP requests and delegates to services
- Service Layer: Contains business rules (booking status transitions)
- Repository Layer: Spring Data JPA for database access
- DTO Layer: Separates request/response models from entities
- GlobalExceptionHandler: Centralized error handling

------------------------------------------------------------

ENTITY RELATIONSHIPS

User (1) → (N) Property (Agent owns listings)
Property (1) → (N) Booking
Property (1) → (N) Lead
User (1) → (N) Booking (Customer)
User (1) → (N) Lead (Customer)

------------------------------------------------------------

CORE FEATURES

Authentication and Authorization:
- Spring Security authentication
- Role-based access control (Admin, Agent, Customer)
- BCrypt password encryption
- Session-based login

Property Management:
- CRUD operations for properties
- Search by title or location
- Property lifecycle: AVAILABLE → BOOKED → SOLD / RENTED

Booking Management:
- Customers can book properties
- Auto status update on booking
- Admin/Agent actions: confirm, cancel, complete

Lead Management:
- Customer inquiries on properties
- Pipeline: NEW → CONTACTED → SITE_VISIT_SCHEDULED → CLOSED / REJECTED

Dashboard:
- Role-based statistics for Admin, Agent, Customer

------------------------------------------------------------

GETTING STARTED

Prerequisites:
- Java 17+
- Maven 3.6+

Run Locally:

git clone https://github.com/dhanushkr018/real-estate-resource-operations-management-system.git
cd real-estate-resource-operations-management-system
mvn clean install
mvn spring-boot:run

------------------------------------------------------------

APPLICATION ACCESS

http://localhost:8080

H2 Console:
http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:realestatedb
Username: sa
Password: (empty)

------------------------------------------------------------

DEMO CREDENTIALS

Admin: admin@realestate.com / admin123
Agent: agent@realestate.com / agent123
Customer: customer@realestate.com / customer123

------------------------------------------------------------

PROJECT STRUCTURE

src/main/java/com/realestate
- config
- controller
- dto
- entity
- repository
- service
  - impl
- exception

src/main/resources
- templates
- static
- application.yml

------------------------------------------------------------

DESIGN DECISIONS

- H2 database used for zero setup convenience
- Session-based authentication for server-rendered UI
- Business logic kept in service layer for clean separation
- Centralized status transitions for consistency

------------------------------------------------------------

FUTURE ENHANCEMENTS

- JWT-based REST APIs for React/Angular frontend
- Payment gateway integration
- Cloud storage for images/documents
- Email/SMS notifications
- Advanced search and pagination
- Analytics dashboard

------------------------------------------------------------

AUTHOR

Dhanush K R

This project demonstrates:
- Spring Boot backend development
- Spring Security authentication
- JPA/Hibernate design
- MVC architecture with Thymeleaf
- Enterprise-level layered application structure



