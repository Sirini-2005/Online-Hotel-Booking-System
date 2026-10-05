package com.booking.controller;

import com.booking.model.Booking;
import com.booking.model.BookingDetails;
import com.booking.service.BookingService;

import java.util.List;

public class BookingController {

    private final BookingService bookingService;

    public BookingController(
            BookingService bookingService) {

        this.bookingService =
                bookingService;
    }

    public void createBooking(
            Booking booking) {

        bookingService.createBooking(
                booking
        );
    }

    public Booking getBookingById(
            Long bookingId) {

        return bookingService.getBookingById(
                bookingId
        );
    }

    public List<Booking> getAllBookings() {

        return bookingService.getAllBookings();
    }

    public List<Booking> getBookingsByUserId(
            Long userId) {

        return bookingService.getBookingsByUserId(
                userId
        );
    }

    public List<Booking> getBookingsByHotelId(
            Long hotelId) {

        return bookingService.getBookingsByHotelId(
                hotelId
        );
    }

    public void updateBooking(
            Booking booking) {

        bookingService.updateBooking(
                booking
        );
    }

    public void cancelBooking(
            Long bookingId) {

        bookingService.cancelBooking(
                bookingId
        );
    }

    public void deleteBooking(
            Long bookingId) {

        bookingService.deleteBooking(
                bookingId
        );
    }

    public List<BookingDetails>
    getAllBookingDetails() {

        return bookingService
                .getAllBookingDetails();
    }
}