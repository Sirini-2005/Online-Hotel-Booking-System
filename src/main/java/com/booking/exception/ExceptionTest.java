package com.booking.exception;

public class ExceptionTest {

    public static void main(String[] args) {

        try {

            throw new ValidationException(
                    "Email is required"
            );

        } catch (Exception e) {

            GlobalExceptionHandler.handle(e);
        }
    }
}