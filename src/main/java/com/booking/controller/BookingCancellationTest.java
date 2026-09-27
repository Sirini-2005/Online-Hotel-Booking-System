package com.booking.controller;

import com.booking.exception.ValidationException;
import com.booking.model.Booking;
import com.booking.util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class BookingCancellationTest {

    public static void main(String[] args) {

        try {

            BookingController bookingController =
                    new BookingController();

            // Get an existing confirmed booking
            Long bookingId = getExistingBookingId();

            if (bookingId == null) {
                System.out.println(
                        "No confirmed booking found!"
                );
                return;
            }

            System.out.println(
                    "Testing cancellation for Booking ID: "
                            + bookingId
            );

            // Check booking before cancellation
            Booking booking =
                    bookingController.getBookingById(bookingId);

            if (booking == null) {
                System.out.println(
                        "Booking not found!"
                );
                return;
            }

            System.out.println(
                    "Booking before cancellation: "
                            + booking.getBookingStatus()
            );

            // Cancel booking
            bookingController.cancelBooking(bookingId);

            // Check booking after cancellation
            Booking cancelledBooking =
                    bookingController.getBookingById(bookingId);

            if (cancelledBooking != null) {

                System.out.println(
                        "Booking after cancellation: "
                                + cancelledBooking.getBookingStatus()
                );

                System.out.println(
                        "Booking cancelled successfully!"
                );
            }

        } catch (ValidationException e) {

            System.out.println(
                    "Cancellation validation failed: "
                            + e.getMessage()
            );

        } catch (Exception e) {

            System.out.println(
                    "Error occurred!"
            );

            e.printStackTrace();
        }
    }

    private static Long getExistingBookingId()
            throws Exception {

        String sql =
                "SELECT booking_id " +
                        "FROM booking " +
                        "WHERE booking_status = 'CONFIRMED' " +
                        "ORDER BY booking_id DESC " +
                        "LIMIT 1";

        try (Connection connection =
                     DBConnection.getConnection();
             Statement statement =
                     connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery(sql)) {

            if (resultSet.next()) {
                return resultSet.getLong("booking_id");
            }
        }

        return null;
    }
}