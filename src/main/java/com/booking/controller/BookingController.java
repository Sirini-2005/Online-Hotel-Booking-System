package com.booking.controller;

import com.booking.daoimpl.BookingDAOImpl;
import com.booking.model.Booking;
import com.booking.service.BookingService;

import java.sql.SQLException;
import java.util.List;

public class BookingController {

    private final BookingService bookingService;

    public BookingController() {
        this.bookingService = new BookingService(
                new BookingDAOImpl()
        );
    }

    // Create booking
    public void createBooking(Booking booking)
            throws SQLException {

        bookingService.createBooking(booking);
    }

    // Get booking by ID
    public Booking getBookingById(Long bookingId)
            throws SQLException {

        return bookingService.getBookingById(bookingId);
    }

    // Get all bookings
    public List<Booking> getAllBookings()
            throws SQLException {

        return bookingService.getAllBookings();
    }

    // Get bookings by user
    public List<Booking> getBookingsByUserId(Long userId)
            throws SQLException {

        return bookingService.getBookingsByUserId(userId);
    }

    // Get bookings by hotel
    public List<Booking> getBookingsByHotelId(Long hotelId)
            throws SQLException {

        return bookingService.getBookingsByHotelId(hotelId);
    }

    // Update booking
    public void updateBooking(Booking booking)
            throws SQLException {

        bookingService.updateBooking(booking);
    }

    // Delete booking
    public void deleteBooking(Long bookingId)
            throws SQLException {

        bookingService.deleteBooking(bookingId);
    }

    // Cancel booking
    public void cancelBooking(Long bookingId)
            throws SQLException {

        bookingService.cancelBooking(bookingId);
    }
}