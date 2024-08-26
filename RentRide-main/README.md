# RentRead

## Problem Statement

Develop a RESTFul API service using Spring Boot to manage an online book rental system while using MySQL to persist the data.

## Features

- **Book Management**: Create, Update, Delete books.
- **User Management**: Create(Register), Update, Delete users.
- **Rental Management**: Rent and Return Book. A User can have maximum 2 books.

## Technologies Used

- **Spring Boot**: Facilitates rapid development and deployment of Java applications.
- **Spring Data JPA**: Simplifies database operations with ORM (Object-Relational Mapping) capabilities.
- **Spring Web**: Supports building web applications using the MVC (Model-View-Controller) pattern.
- **Spring Validation**: Ensures data integrity and validates input requests.
- **MySQL**: Database management system used for persistent data storage.
- **Lombok**: Reduces boilerplate code by automatically generating getters, setters, constructors, etc.
- **Swagger UI**: Provides interactive documentation for RestFul APIs.

## Setup Procedure

### Prerequisites

- JDK 11 or higher installed
- Maven build tool installed
- Docker installed (for MySQL database)

### Steps to Setup

1. Clone the repository
   ``` bash
      git clone https://github.com/vanshikaaa19/RentRide
      cd RentRide
   ```
2. Start MySQL Database
   Ensure Docker is running, then execute:
    ```bash
    docker-compose up -d
   ```
3. Build and Run the Application
    ```bash
    mvn clean install
    mvn spring-boot:run
    ``` 
4. Access Swagger UI
   Open a web browser and navigate to:
    ```bash
    http://localhost:8080/swagger-ui.html
    ```
   Swagger UI will display all available endpoints and allow you to interact with the API.
5. Import Postman Collection

   [Postman-Collection-File](./External_Resource/RentRead.postman_collection.json)

   [Step-by-Step Procedure](https://learning.postman.com/docs/getting-started/importing-and-exporting/importing-data/)
6. Requirement:

   [Problem Statement](./External_Resource/Week%203%20-%20Problem%20Statement_%20RentRead.pdf)


### Usage

- Use Swagger UI to test and explore different endpoints.
- Perform CRUD operations on students, exams, subjects, and contests as needed.
- Integrate additional features or extend functionalities based on project requirements.
