package com.booking.service;

import com.booking.dao.BookingDAO;
import com.booking.daoimpl.BookingDAOImpl;
import com.booking.exception.ValidationException;
import com.booking.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BookingService {

    private static final Logger logger =
            LoggerFactory.getLogger(BookingService.class);

    private final BookingDAO bookingDAO;

    public BookingService() {
        this.bookingDAO = new BookingDAOImpl();
    }

    public BookingService(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    public void createBooking(Booking booking) throws SQLException {

        logger.info("Creating booking");

        if (booking == null) {
            logger.warn("Booking is null");
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

        if (booking.getCheckInDate() == null ||
                booking.getCheckOutDate() == null) {
            throw new ValidationException("Booking dates are required");
        }

        if (!booking.getCheckOutDate()
                .isAfter(booking.getCheckInDate())) {
            throw new ValidationException(
                    "Check-out date must be after check-in date"
            );
        }

        if (booking.getGuests() == null ||
                booking.getGuests() <= 0) {
            throw new ValidationException(
                    "Guests must be greater than 0"
            );
        }

        if (booking.getTotalAmount() == null ||
                booking.getTotalAmount() < 0) {
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

        boolean available = bookingDAO.isRoomAvailable(
                booking.getRoomId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate()
        );

        if (!available) {
            logger.warn("Room is not available");
            throw new ValidationException(
                    "Room is not available for selected dates"
            );
        }

        bookingDAO.save(booking);

        logger.info("Booking created successfully");
    }

    public Booking getBookingById(Long bookingId)
            throws SQLException {

        logger.info("Getting booking by ID");

        return bookingDAO.findById(bookingId);
    }

    public List<Booking> getAllBookings()
            throws SQLException {

        logger.info("Getting all bookings");

        return bookingDAO.findAll();
    }

    public List<Booking> getBookingsByUserId(Long userId)
            throws SQLException {

        logger.info("Getting bookings by user");

        return bookingDAO.findByUserId(userId);
    }

    public List<Booking> getBookingsByHotelId(Long hotelId)
            throws SQLException {

        logger.info("Getting bookings by hotel");

        return bookingDAO.findByHotelId(hotelId);
    }

    public boolean isRoomAvailable(
            Long roomId,
            LocalDate checkInDate,
            LocalDate checkOutDate)
            throws SQLException {

        logger.info("Checking room availability");

        return bookingDAO.isRoomAvailable(
                roomId,
                checkInDate,
                checkOutDate
        );
    }

    public void cancelBooking(Long bookingId)
            throws SQLException {

        logger.info("Cancelling booking");

        bookingDAO.cancelBooking(bookingId);

        logger.info("Booking cancelled successfully");
    }

    public void updateBooking(Booking booking)
            throws SQLException {

        logger.info("Updating booking");

        bookingDAO.update(booking);

        logger.info("Booking updated successfully");
    }

    public void deleteBooking(Long bookingId)
            throws SQLException {

        logger.info("Deleting booking");

        bookingDAO.delete(bookingId);

        logger.info("Booking deleted successfully");
    }
}