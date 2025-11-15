# Student Management REST API (Spring Boot 3, Java 17)

A clean CRUD REST API for managing students with validation, service layer, global exception handling, pagination/sorting/search, and Swagger UI.

## Table of Contents

- Overview
- Tech Stack
- Features
- Architecture & Project Structure
- Prerequisites
- Getting Started
  - Run with in-memory H2 (default)
  - Run with MySQL
  - Build and run JAR
- Configuration (properties and environment variables)
- Database
  - Entity model
  - MySQL DDL
- API Documentation (Swagger/OpenAPI)
- Endpoints and Sample Requests
- Postman
- cURL Quick Start
- Error Handling
- Pagination, Sorting, and Search
- Docker (optional)
- Troubleshooting

## Overview

This service manages Students with the following fields: `id`, `name`, `email`, `course`, `age`. Email is unique. The API exposes endpoints to create, list (with pagination/sorting/search), get by id, update, and delete.

## Tech Stack

- Spring Boot 3 (Java 17)
- Spring Web, Spring Data JPA, Validation
- H2 (default), MySQL
- Swagger UI via springdoc-openapi

## Features

- Create, Read (paged + search), Update, Delete students
- Validation: required fields, valid email, age >= 18
- Global exception handling with proper HTTP statuses
- Swagger UI for interactive API exploration

## Architecture & Project Structure

```
src/
  main/
    java/com/example/studentapi/
      StudentManagementApiApplication.java
      model/Student.java
      repository/StudentRepository.java
      service/StudentService.java
      service/impl/StudentServiceImpl.java
      controller/StudentController.java
      exception/GlobalExceptionHandler.java
      exception/ResourceNotFoundException.java
      config/OpenApiConfig.java
    resources/
      application.properties
      # (Optional, if present) application-h2.properties, application-mysql.properties
```

## Prerequisites

- Java 17+
- Maven 3.9+
- Optional: MySQL 8.x (if running with MySQL)

## Getting Started

### Run with H2 (default — zero setup)

```bash
mvn spring-boot:run
```

App: `http://localhost:8080`

H2 Console: `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:studentdb`
- User: `sa` (no password)

Swagger UI: `http://localhost:8080/swagger-ui.html`

### Run with MySQL

1. Start MySQL and create database/user (replace values as needed):

```sql
CREATE DATABASE IF NOT EXISTS studentdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'student_user'@'%' IDENTIFIED BY 'StrongP@ssw0rd!';
GRANT ALL PRIVILEGES ON studentdb.* TO 'student_user'@'%';
FLUSH PRIVILEGES;
```

2. Configure Spring to use MySQL. You can choose one of the following:

- Option A: Edit `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/studentdb?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=student_user
spring.datasource.password=StrongP@ssw0rd!
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

- Option B: Provide overrides via environment variables (no file edits)

```bash
# Windows PowerShell example
$Env:SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/studentdb?useSSL=false&allowPublicKeyRetrieval=true"
$Env:SPRING_DATASOURCE_USERNAME="student_user"
$Env:SPRING_DATASOURCE_PASSWORD="StrongP@ssw0rd!"
mvn spring-boot:run
```

3. Run the application:

```bash
mvn spring-boot:run
```

App: `http://localhost:8080`  
Swagger UI: `http://localhost:8080/swagger-ui.html`

### Build and run JAR

```bash
mvn clean package -DskipTests
java -jar target/student-management-api-0.0.1-SNAPSHOT.jar
```

With MySQL via env vars:

```bash
$Env:SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/studentdb?useSSL=false&allowPublicKeyRetrieval=true"
$Env:SPRING_DATASOURCE_USERNAME="student_user"
$Env:SPRING_DATASOURCE_PASSWORD="StrongP@ssw0rd!"
java -jar target/student-management-api-0.0.1-SNAPSHOT.jar
```

## Configuration

Common JPA properties (can live in `application.properties`):

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# Swagger
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/v3/api-docs
```

## Database

### Entity model

Student fields:

```json
{
  "id": 1,
  "name": "Avishka Shehan",
  "email": "Avishka@gmail.com",
  "course": "SE",
  "age": 25
}
```

Validation rules:

- name, email, course: required and non-blank
- email: must be valid format and unique
- age: integer >= 18

### MySQL DDL

```sql
CREATE TABLE IF NOT EXISTS students (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  course VARCHAR(255) NOT NULL,
  age INT NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_students_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Optional indexes for search
CREATE INDEX idx_students_name ON students (name);
CREATE INDEX idx_students_course ON students (course);
```

## API Documentation (Swagger/OpenAPI)

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

Open the UI, expand each endpoint, click “Try it out”, fill body/params, and Execute. You will see the request, response, and auto-generated curl.

## Endpoints and Sample Requests

- POST `/api/students` — Create (201 Created)

```http
POST /api/students
Content-Type: application/json

{
  "name": "Alice Johnson",
  "email": "alice@example.com",
  "course": "Computer Science",
  "age": 20
}
```

- GET `/api/students` — List (200 OK) with pagination/sorting/search
  - Query params: `page`, `size`, `sort=field,asc|desc`, `q` (search in `name` or `course`, case-insensitive)

Examples:

- `/api/students?page=0&size=5&sort=name,asc`
- `/api/students?q=cs`

- GET `/api/students/{id}` — Get by id (200 OK / 404 Not Found)

- PUT `/api/students/{id}` — Update (200 OK / 404 Not Found)

```http
PUT /api/students/1
Content-Type: application/json

{
  "name": "Alice J.",
  "email": "alice.j@example.com",
  "course": "Software Engineering",
  "age": 21
}
```

- DELETE `/api/students/{id}` — Delete (204 No Content / 404 Not Found)

## Postman

Import the OpenAPI spec:

1. Open Postman → Import → Link
2. Paste `http://localhost:8080/v3/api-docs`
3. Postman creates a collection with all endpoints.

### Postman Screenshots

#### Create Student (POST)

![Add Student](images/Add%20Student.JPG)

#### Get All Students (GET)

![Get All Student](images/Get%20All%20Student.JPG)

#### Get Student by ID (GET)

![Get Student by ID](images/Get%20Student%20by%20ID.JPG)

#### Update Student (PUT)

![Update Student](images/Update%20Student.JPG)

#### Delete Student (DELETE)

![Delete Student](images/Delete%20Student.JPG)

## cURL Quick Start

- Create

```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice Johnson","email":"alice@example.com","course":"Computer Science","age":20}'
```

- List (page + search)

```bash
curl "http://localhost:8080/api/students?page=0&size=5&sort=name,asc&q=cs"
```

- Get by id

```bash
curl http://localhost:8080/api/students/1
```

- Update

```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice J.","email":"alice.j@example.com","course":"Software Engineering","age":21}'
```

- Delete

```bash
curl -X DELETE http://localhost:8080/api/students/1
```

## Error Handling

Common responses:

- 400 Bad Request — Validation errors (e.g., missing fields, invalid email, age < 18, duplicate email)
- 404 Not Found — Resource not found (e.g., student id not found)
- 201 Created — On successful creation (Location header points to new resource)
- 204 No Content — On successful deletion

## Pagination, Sorting, and Search

- Pagination: `page` (0-based) and `size`
- Sorting: `sort=field,dir` (e.g., `sort=name,asc`)
- Search: `q` filters by `name` OR `course` (case-insensitive match)

Examples:

- `/api/students?page=1&size=10&sort=age,desc`
- `/api/students?q=software`

## Docker (optional)

Run MySQL with Docker:

```bash
docker run --name mysql-studentdb -e MYSQL_ROOT_PASSWORD=your_root_password \
  -e MYSQL_DATABASE=studentdb -p 3306:3306 -d mysql:8
```

Then point Spring to `jdbc:mysql://localhost:3306/studentdb?...`.

## Troubleshooting

- “release version 5 not supported”
  - Ensure Java 17 is used. We set Maven compiler to `<release>17</release>` in `pom.xml`. Also configure your IDE Project SDK to 17.
- “Access denied” or “Communications link failure” with MySQL
  - Verify DB is running, credentials are correct, and `allowPublicKeyRetrieval=true` is present in JDBC URL for MySQL 8.
- Table not created
  - Ensure `spring.jpa.hibernate.ddl-auto=update` is set when using MySQL/H2 in dev.
- Swagger UI not loading
  - Confirm the app is running and access `http://localhost:8080/swagger-ui.html`. Check logs for errors.
