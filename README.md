# User Management System with Spring Boot and Spring Security

This project is designed to serve as a comprehensive user management system for web applications built on the Spring Boot framework. It aims to provide a practical demonstration of Spring Security's capabilities, offering an opportunity to explore and understand the security hierarchy within a Spring API.

## Prerequisites

Ensure you have the following prerequisites installed before running the project:

- [Java 17](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Spring Boot 3.1.5](https://spring.io/projects/spring-boot)
- [Spring Security 6.2](https://spring.io/projects/spring-security)

## Getting Started

Follow these steps to get the project up and running:

1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Run the application using the following command:

   ```bash
   ./mvnw spring-boot:run
Access the API at http://localhost:8080.

## API Endpoints

### Registration

- **Endpoint:** `/api/v1/auth/signup`
  - **Method:** `POST`
  - **Description:** Register a new user.
  - **Request Body:**
    ```json
    {
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "password": "your_secure_password"
    }
    ```
  - **Response:**
    ```json
    {
    "id": {
        "timestamp": 1703024114,
        "date": "2023-12-19T22:15:14.000+00:00"
    },
    "firstName": "Maaaaaaa",
    "lastName": "WWWWW",
    "email": "asdasdasd@gmail.com",
    "password": "$2a$10$Iailuo5IQXXPe/lgpKgUjuk8plVxdj.veBRqNtqrhFw0BF2Ea4rRa",
    // ... Other properties
    }
    ```

### Authentication

- **Endpoint:** `/api/v1/auth/login`
  - **Method:** `POST`
  - **Description:** User login; returns a JWT token and a refresh token.
  - **Request Body:**
    ```json
    {
      "email": "john.doe@example.com",
      "password": "your_secure_password"
    }
    ```
  - **Response:**
    ```json
    {
      "token": "your_jwt_access_token",
      "refreshToken": "your_refresh_token"
    }
    ```

- **Endpoint:** `/api/v1/auth/refresh`
  - **Method:** `POST`
  - **Description:** Refresh the access token using the refresh token.
  - **Request Body:**
    ```json
    {
      "token": "your_refresh_token"
    }
    ```
  - **Response:**
    ```json
    {
      "token": "your_new_jwt_access_token",
      "refreshToken": "your_refresh_token"
    }
    ```

### Admin Operations

- **Endpoint:** `/api/v1/admin/listUsers`
  - **Method:** `GET`
  - **Description:** Get a list of users (admin access required).
  - **Response:**
    ```json
    [
      {
        "id": 1,
        "firstName": "John",
        "lastName": "Doe",
        "email": "john.doe@example.com",
        "role": "USER"
      },
      // ... other user objects
    ]
    ```

- **Endpoint:** `/api/v1/admin/getUser`
  - **Method:** `GET`
  - **Description:** Get details of a specific user (admin access required).
  - **Query Parameters:**
    - `userId`: User ID to retrieve details.
  - **Response:**
    ```json
    {
      "id": {
        "timestamp": 1703024114,
        "date": "2023-12-19T22:15:14.000+00:00"
      },
      "firstName": "Maaaaaaa",
      "lastName": "WWWWW",
      "email": "asdasdasd@gmail.com",
      //... more properties
    }
    ```

### User Operations

- **Endpoint:** `/api/v1/user/getUser`
  - **Method:** `GET`
  - **Description:** Get the profile of the authenticated user.
  - **Headers:**
    - `Authorization: Bearer your_jwt_access_token`
  - **Response:**
    ```json
    {
      "id": {
        "timestamp": 1703024114,
        "date": "2023-12-19T22:15:14.000+00:00"
      },
      "firstName": "Maaaaaaa",
      "lastName": "WWWWW",
      "email": "asdasdasd@gmail.com",
    // ... more properties
    }
    ```
