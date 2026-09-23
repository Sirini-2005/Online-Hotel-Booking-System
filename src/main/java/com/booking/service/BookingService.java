package com.booking.service;

import com.booking.dao.BookingDAO;
import com.booking.daoimpl.BookingDAOImpl;
import com.booking.exception.ValidationException;
import com.booking.model.Booking;

import java.sql.SQLException;
import java.util.List;

public class BookingService {

    private final BookingDAO bookingDAO;

    public BookingService() {
        this.bookingDAO = new BookingDAOImpl();
    }

    public void createBooking(Booking booking) throws SQLException {

        if (booking == null) {
            throw new ValidationException("Booking cannot be null");
        }

        if (booking.getUserId() == null) {
            throw new ValidationException("User ID is required");
        }

        if (booking.getHotelId() == null) {
            throw new ValidationException("Hotel ID is required");
        }

        if (booking.getRoomId() == null) {
            throw new ValidationException("Room ID is required");
        }

        if (booking.getCheckInDate() == null) {
            throw new ValidationException("Check-in date is required");
        }

        if (booking.getCheckOutDate() == null) {
            throw new ValidationException("Check-out date is required");
        }

        if (!booking.getCheckOutDate().isAfter(booking.getCheckInDate())) {
            throw new ValidationException(
                    "Check-out date must be after check-in date"
            );
        }

        if (booking.getGuests() == null || booking.getGuests() <= 0) {
            throw new ValidationException(
                    "Number of guests must be greater than 0"
            );
        }

        if (booking.getTotalAmount() == null || booking.getTotalAmount() < 0) {
            throw new ValidationException(
                    "Total amount cannot be negative"
            );
        }

        if (booking.getBookingStatus() == null ||
                booking.getBookingStatus().trim().isEmpty()) {
            throw new ValidationException(
                    "Booking status is required"
            );
        }

        bookingDAO.save(booking);
    }

    public Booking getBookingById(Long bookingId) throws SQLException {
        return bookingDAO.findById(bookingId);
    }

    public List<Booking> getAllBookings() throws SQLException {
        return bookingDAO.findAll();
    }

    public List<Booking> getBookingsByUserId(Long userId) throws SQLException {
        return bookingDAO.findByUserId(userId);
    }

    public List<Booking> getBookingsByHotelId(Long hotelId) throws SQLException {
        return bookingDAO.findByHotelId(hotelId);
    }

    public void updateBooking(Booking booking) throws SQLException {
        bookingDAO.update(booking);
    }

    public void deleteBooking(Long bookingId) throws SQLException {
        bookingDAO.delete(bookingId);
    }
}