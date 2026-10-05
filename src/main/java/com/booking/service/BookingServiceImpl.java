package com.booking.service;

import com.booking.dao.BookingDAO;
import com.booking.exception.BookingException;
import com.booking.exception.DatabaseException;
import com.booking.exception.ValidationException;
import com.booking.model.Booking;
import com.booking.model.BookingDetails;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BookingServiceImpl implements BookingService {

    private final BookingDAO bookingDAO;

    public BookingServiceImpl(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @Override
    public void createBooking(Booking booking) {

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
            throw new ValidationException("Check-out date must be after check-in date");
        }

        if (booking.getGuests() == null || booking.getGuests() <= 0) {
            throw new ValidationException("Guests must be greater than 0");
        }

        if (booking.getTotalAmount() == null || booking.getTotalAmount() < 0) {
            throw new ValidationException("Total amount cannot be negative");
        }

        try {
            boolean available = bookingDAO.isRoomAvailable(
                    booking.getRoomId(),
                    booking.getCheckInDate(),
                    booking.getCheckOutDate()
            );

            if (!available) {
                throw new BookingException("Room is not available for the selected dates");
            }

            if (booking.getBookingStatus() == null ||
                    booking.getBookingStatus().isBlank()) {
                booking.setBookingStatus("PENDING");
            }

            bookingDAO.save(booking);

        } catch (BookingException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseException("Failed to create booking", e);
        }
    }

    @Override
    public Booking getBookingById(Long bookingId) {

        if (bookingId == null || bookingId <= 0) {
            throw new ValidationException("Invalid booking ID");
        }

        try {
            return bookingDAO.findById(bookingId);
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch booking", e);
        }
    }

    @Override
    public List<Booking> getAllBookings() {

        try {
            return bookingDAO.findAll();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch bookings", e);
        }
    }

    @Override
    public List<Booking> getBookingsByUserId(Long userId) {

        if (userId == null || userId <= 0) {
            throw new ValidationException("Invalid user ID");
        }

        try {
            return bookingDAO.findByUserId(userId);
        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to fetch bookings for user",
                    e
            );
        }
    }

    @Override
    public List<Booking> getBookingsByHotelId(Long hotelId) {

        if (hotelId == null || hotelId <= 0) {
            throw new ValidationException("Invalid hotel ID");
        }

        try {
            return bookingDAO.findByHotelId(hotelId);
        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to fetch bookings for hotel",
                    e
            );
        }
    }

    @Override
    public void updateBooking(Booking booking) {

        if (booking == null) {
            throw new ValidationException("Booking cannot be null");
        }

        if (booking.getBookingId() == null ||
                booking.getBookingId() <= 0) {
            throw new ValidationException("Invalid booking ID");
        }

        if (booking.getCheckInDate() == null ||
                booking.getCheckOutDate() == null) {
            throw new ValidationException("Check-in and check-out dates are required");
        }

        if (!booking.getCheckOutDate().isAfter(
                booking.getCheckInDate())) {
            throw new ValidationException(
                    "Check-out date must be after check-in date"
            );
        }

        if (booking.getGuests() == null || booking.getGuests() <= 0) {
            throw new ValidationException("Guests must be greater than 0");
        }

        if (booking.getTotalAmount() == null ||
                booking.getTotalAmount() < 0) {
            throw new ValidationException(
                    "Total amount cannot be negative"
            );
        }

        try {
            bookingDAO.update(booking);
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update booking", e);
        }
    }

    @Override
    public void cancelBooking(Long bookingId) {

        if (bookingId == null || bookingId <= 0) {
            throw new ValidationException("Invalid booking ID");
        }

        try {
            Booking booking = bookingDAO.findById(bookingId);

            if (booking == null) {
                throw new BookingException("Booking not found");
            }

            if ("CANCELLED".equalsIgnoreCase(
                    booking.getBookingStatus())) {
                throw new BookingException("Booking is already cancelled");
            }

            bookingDAO.cancelBooking(bookingId);

        } catch (BookingException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to cancel booking",
                    e
            );
        }
    }

    @Override
    public void deleteBooking(Long bookingId) {

        if (bookingId == null || bookingId <= 0) {
            throw new ValidationException("Invalid booking ID");
        }

        try {
            bookingDAO.delete(bookingId);
        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to delete booking",
                    e
            );
        }
    }

    @Override
    public List<BookingDetails> getAllBookingDetails() {

        try {
            return bookingDAO.findAllBookingDetails();
        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to fetch complete booking details",
                    e
            );
        }
    }
}