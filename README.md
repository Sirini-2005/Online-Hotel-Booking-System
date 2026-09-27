# Online Hotel Booking System

## Project Overview

The Online Hotel Booking System is a Java-based application designed to manage hotel information, rooms, users, bookings, payments, reviews, and hotel images.

The project follows a layered architecture to keep the application organized, maintainable, and easy to test.

## Technologies Used

- Java 21
- JDBC
- MySQL
- Maven
- JUnit 5
- Mockito
- SLF4J
- Logback
- Git & GitHub
- IntelliJ IDEA

## Architecture

MainController
↓
Controllers
↓
Services
↓
DAOs
↓
JDBC
↓
MySQL

## Main Modules

### User
Manages user registration, user details, roles, and status.

### Location
Manages locations and hierarchical location information.

### Hotel
Manages hotel details, location, rating, amenities, and status.

### Room
Manages rooms, room types, capacity, pricing, and availability status.

### Booking
Manages hotel room bookings, booking dates, guests, amount, availability, and cancellation.

### Payment
Manages payment information and transaction references.

### Review
Manages hotel ratings and customer reviews.

### Hotel Image
Manages hotel image URLs and captions.

## Project Layers

### Model Layer
Contains Java classes representing database entities.

Examples:
- User
- Location
- Hotel
- Room
- Booking
- Payment
- Review
- HotelImage

### DAO Layer

DAO interfaces define database operations such as:

- Save
- Find by ID
- Find all
- Update
- Delete
- Module-specific search operations

### DAO Implementation Layer

The DAO implementation classes use JDBC and PreparedStatement to communicate with MySQL.

### Service Layer

The service layer contains business logic and validation before database operations.

Examples:

- Required field validation
- Rating validation
- Room capacity validation
- Price validation
- Booking date validation
- Room availability validation

### Controller Layer

Controllers receive application requests and call the appropriate service methods.

## Exception Handling

The project contains custom exceptions:

- ValidationException
- BookingException
- DatabaseException

A GlobalExceptionHandler is used to handle application exceptions.

## Logging

SLF4J is used as the logging interface and Logback is used as the logging implementation.

Logging is used to record:

- Application startup
- Database operations
- Service operations
- Validation failures
- Errors and exceptions

## Testing

JUnit 5 is used for unit testing.

Mockito is used for mocking dependencies where required.

The project includes tests for:

- User service
- Location service
- Hotel service
- Room service
- Booking service
- Payment service
- Review service
- Hotel image service

Integration testing was also performed to verify the interaction between the application and database.

## Database

The application uses MySQL database:

hotel_booking_system

Main tables:

- user
- location
- hotel
- room
- booking
- payment
- review
- hotel_image

## How to Run

1. Create the MySQL database.
2. Create the required tables using the project SQL schema.
3. Configure the database connection in `DBConnection`.
4. Load the Maven dependencies.
5. Run the application from IntelliJ IDEA.
6. Run the JUnit tests to verify the application.

## Project Status

- Database schema completed
- Model layer completed
- DAO layer completed
- Service layer completed
- Validation completed
- Exception handling completed
- Logging completed
- Controller layer completed
- Unit testing completed
- Integration testing completed
- GitHub repository updated