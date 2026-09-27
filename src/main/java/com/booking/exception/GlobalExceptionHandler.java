package com.booking.exception;

public class GlobalExceptionHandler {

    public static void handle(Exception exception) {

        if (exception instanceof ValidationException) {
            System.out.println(
                    "Validation Error: " + exception.getMessage()
            );

        } else if (exception instanceof BookingException) {
            System.out.println(
                    "Booking Error: " + exception.getMessage()
            );

        } else if (exception instanceof DatabaseException) {
            System.out.println(
                    "Database Error: " + exception.getMessage()
            );

        } else {
            System.out.println(
                    "Unexpected Error: " + exception.getMessage()
            );
        }
    }
}