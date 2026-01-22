# OpsFlow

A backend task management system with role-based permissions for task workflows,and comments.  

## Tech Stack
- Spring Boot
- Spring Security (JWT)
- Spring Dta JPA
- PostgreSQL
- Flyway
- Docker
- Maven
- Github Actions (CI)

## Features
- User authentication (JWT)
- Roles: ADMIN, MANAGER, EMPLOYEE
- Task creation, assignment, and status
- Role-based authorization to support workflow
- Task comments with role-based authorization 

## Architecture
- Controllers handle only HTTP requests and responses
- Service layer enforcing business rules and logic
- Repositories handle database access through Spring Data JPA
- JWT authentication handled with a security filter
- Flyway handles database schema migrations

## API Endpoints
- POST   /auth/register
- POST   /auth/login
- POST   /tasks
- GET    /tasks
- GET    /tasks/{id}
- PUT    /tasks/{id}/assign
- PUT    /tasks/{id}/status
- POST   /tasks/{id}/comments
- GET    /tasks/{id}/comments

## Run Locally
- docker compose up
- ./mvnw spring-boot:run