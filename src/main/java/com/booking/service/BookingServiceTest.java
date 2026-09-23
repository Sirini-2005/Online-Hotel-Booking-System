package com.booking.service;

import com.booking.model.Booking;

import java.time.LocalDate;

public class BookingServiceTest {

    public static void main(String[] args) {

        try {

            BookingService bookingService = new BookingService();

            Booking booking = new Booking();

            booking.setUserId(1L);
            booking.setHotelId(1L);
            booking.setRoomId(1L);

            booking.setCheckInDate(
                    LocalDate.of(2026, 10, 1)
            );

            booking.setCheckOutDate(
                    LocalDate.of(2026, 10, 3)
            );

            booking.setGuests(2);
            booking.setTotalAmount(5000.00);
            booking.setBookingStatus("CONFIRMED");

            bookingService.createBooking(booking);

            System.out.println("Booking created successfully!");
            System.out.println("Booking validation passed.");

        } catch (Exception e) {

            System.out.println("Error occurred!");
            e.printStackTrace();
        }
    }
}