# RentRide
RentRead
Problem Statement
Develop a RESTFul API service using Spring Boot to manage an online book rental system while using MySQL to persist the data.

Features
Book Management: Create, Update, Delete books.
User Management: Create(Register), Update, Delete users.
Rental Management: Rent and Return Book. A User can have maximum 2 books.
Technologies Used
Spring Boot: Facilitates rapid development and deployment of Java applications.
Spring Data JPA: Simplifies database operations with ORM (Object-Relational Mapping) capabilities.
Spring Web: Supports building web applications using the MVC (Model-View-Controller) pattern.
Spring Validation: Ensures data integrity and validates input requests.
MySQL: Database management system used for persistent data storage.
Lombok: Reduces boilerplate code by automatically generating getters, setters, constructors, etc.
Swagger UI: Provides interactive documentation for RestFul APIs.
Setup Procedure
Prerequisites
JDK 11 or higher installed
Maven build tool installed
Docker installed (for MySQL database)
Steps to Setup
Clone the repository

   git clone https://github.com/PushpanshuRanjanSingh/RentRide
   cd RentRide
Start MySQL Database Ensure Docker is running, then execute:

docker-compose up -d
Build and Run the Application

mvn clean install
mvn spring-boot:run
Access Swagger UI Open a web browser and navigate to:

http://localhost:8080/swagger-ui.html
Swagger UI will display all available endpoints and allow you to interact with the API.

Import Postman Collection

Postman-Collection-File

Step-by-Step Procedure

Requirement:

Problem Statement

Usage
Use Swagger UI to test and explore different endpoints.
Perform CRUD operations on students, exams, subjects, and contests as needed.
Integrate additional features or extend functionalities based on project requirements.
About
Develop a RESTFul API service using Spring Boot to manage an online book rental system while using MySQL to persist the data.

Topics
mysql java maven springboot jpa-hibernate
Resources
 Readme
 Activity
Stars
 0 stars
Watchers
 1 watching
Forks
 0 forks
Report repository
Releases
No releases published
Packages
No packages published
Languages
Java
100.0%
Footer
© 2024 GitHub, Inc.
Footer navigation
Terms
Privacy
Security
Status
Docs
Contact
