package com.booking.controller;

import com.booking.exception.ValidationException;
import com.booking.model.Booking;

import java.sql.SQLException;
import java.time.LocalDate;

public class BookingControllerTest {

    public static void main(String[] args) {

        try {

            // Create Booking Controller
            BookingController bookingController =
                    new BookingController();

            // Create Booking object
            Booking booking = new Booking();

            // Existing User, Hotel and Room IDs
            booking.setUserId(1L);
            booking.setHotelId(1L);
            booking.setRoomId(2L);

            // Future booking dates
            // Using a far-future date to avoid existing bookings
            booking.setCheckInDate(
                    LocalDate.of(2030, 1, 10)
            );

            booking.setCheckOutDate(
                    LocalDate.of(2030, 1, 15)
            );

            // Number of guests
            booking.setGuests(2);

            // Total booking amount
            booking.setTotalAmount(5000.0);

            // Booking status
            booking.setBookingStatus("CONFIRMED");

            // Create booking through Controller
            bookingController.createBooking(booking);

            System.out.println(
                    "Booking created successfully!"
            );

        } catch (ValidationException e) {

            System.out.println(
                    "Booking validation failed: "
                            + e.getMessage()
            );

        } catch (SQLException e) {

            System.out.println(
                    "Database error: "
                            + e.getMessage()
            );

            e.printStackTrace();

        } catch (Exception e) {

            System.out.println(
                    "Unexpected error: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}