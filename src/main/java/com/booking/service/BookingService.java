package com.booking.service;

import com.booking.dao.BookingDAO;
import com.booking.exception.ValidationException;
import com.booking.model.Booking;
import com.booking.util.LoggerUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class BookingService {

    private final BookingDAO bookingDAO;

    private static final Logger logger =
            LoggerUtil.getLogger(BookingService.class);

    public BookingService(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    public void createBooking(Booking booking) throws SQLException {

        if (booking == null) {
            logger.warning("Booking creation failed: Booking is null");
            throw new ValidationException("Booking cannot be null");
        }

        if (booking.getUserId() == null) {
            logger.warning("Booking creation failed: User ID is missing");
            throw new ValidationException("User ID is required");
        }

        if (booking.getHotelId() == null) {
            logger.warning("Booking creation failed: Hotel ID is missing");
            throw new ValidationException("Hotel ID is required");
        }

        if (booking.getRoomId() == null) {
            logger.warning("Booking creation failed: Room ID is missing");
            throw new ValidationException("Room ID is required");
        }

        if (booking.getCheckInDate() == null) {
            logger.warning("Booking creation failed: Check-in date is missing");
            throw new ValidationException("Check-in date is required");
        }

        if (booking.getCheckOutDate() == null) {
            logger.warning("Booking creation failed: Check-out date is missing");
            throw new ValidationException("Check-out date is required");
        }

        if (!booking.getCheckOutDate().isAfter(booking.getCheckInDate())) {
            logger.warning("Booking creation failed: Invalid check-in/check-out dates");
            throw new ValidationException(
                    "Check-out date must be after check-in date"
            );
        }

        if (booking.getGuests() == null || booking.getGuests() <= 0) {
            logger.warning("Booking creation failed: Invalid number of guests");
            throw new ValidationException(
                    "Number of guests must be greater than 0"
            );
        }

        if (booking.getTotalAmount() == null ||
                booking.getTotalAmount() < 0) {

            logger.warning("Booking creation failed: Invalid total amount");

            throw new ValidationException(
                    "Total amount cannot be negative"
            );
        }

        if (booking.getBookingStatus() == null ||
                booking.getBookingStatus().trim().isEmpty()) {

            logger.warning("Booking creation failed: Booking status is missing");

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

            logger.warning(
                    "Booking creation failed: Room "
                            + booking.getRoomId()
                            + " is not available"
            );

            throw new ValidationException(
                    "Room is not available for the selected dates"
            );
        }

        bookingDAO.save(booking);

        logger.info(
                "Booking created successfully for user ID: "
                        + booking.getUserId()
        );
    }

    public Booking getBookingById(Long bookingId) throws SQLException {

        if (bookingId == null) {
            logger.warning("Get booking failed: Booking ID is missing");
            throw new ValidationException("Booking ID is required");
        }

        Booking booking = bookingDAO.findById(bookingId);

        if (booking != null) {
            logger.info(
                    "Booking retrieved successfully. Booking ID: "
                            + bookingId
            );
        } else {
            logger.warning(
                    "Booking not found. Booking ID: "
                            + bookingId
            );
        }

        return booking;
    }

    public List<Booking> getAllBookings() throws SQLException {

        logger.info("Fetching all bookings");

        return bookingDAO.findAll();
    }

    public List<Booking> getBookingsByUserId(Long userId)
            throws SQLException {

        if (userId == null) {
            logger.warning(
                    "Get user bookings failed: User ID is missing"
            );

            throw new ValidationException(
                    "User ID is required"
            );
        }

        logger.info(
                "Fetching bookings for user ID: "
                        + userId
        );

        return bookingDAO.findByUserId(userId);
    }

    public List<Booking> getBookingsByHotelId(Long hotelId)
            throws SQLException {

        if (hotelId == null) {
            logger.warning(
                    "Get hotel bookings failed: Hotel ID is missing"
            );

            throw new ValidationException(
                    "Hotel ID is required"
            );
        }

        logger.info(
                "Fetching bookings for hotel ID: "
                        + hotelId
        );

        return bookingDAO.findByHotelId(hotelId);
    }

    public void updateBooking(Booking booking) throws SQLException {

        if (booking == null) {
            logger.warning(
                    "Booking update failed: Booking is null"
            );

            throw new ValidationException(
                    "Booking cannot be null"
            );
        }

        if (booking.getBookingId() == null) {
            logger.warning(
                    "Booking update failed: Booking ID is missing"
            );

            throw new ValidationException(
                    "Booking ID is required"
            );
        }

        if (booking.getUserId() == null) {
            throw new ValidationException(
                    "User ID is required"
            );
        }

        if (booking.getHotelId() == null) {
            throw new ValidationException(
                    "Hotel ID is required"
            );
        }

        if (booking.getRoomId() == null) {
            throw new ValidationException(
                    "Room ID is required"
            );
        }

        if (booking.getCheckInDate() == null) {
            throw new ValidationException(
                    "Check-in date is required"
            );
        }

        if (booking.getCheckOutDate() == null) {
            throw new ValidationException(
                    "Check-out date is required"
            );
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
                    "Number of guests must be greater than 0"
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

        bookingDAO.update(booking);

        logger.info(
                "Booking updated successfully. Booking ID: "
                        + booking.getBookingId()
        );
    }

    public void deleteBooking(Long bookingId) throws SQLException {

        if (bookingId == null) {
            logger.warning(
                    "Booking deletion failed: Booking ID is missing"
            );

            throw new ValidationException(
                    "Booking ID is required"
            );
        }

        bookingDAO.delete(bookingId);

        logger.info(
                "Booking deleted successfully. Booking ID: "
                        + bookingId
        );
    }

    public void cancelBooking(Long bookingId) throws SQLException {

        if (bookingId == null) {
            logger.warning(
                    "Booking cancellation failed: Booking ID is missing"
            );

            throw new ValidationException(
                    "Booking ID is required"
            );
        }

        Booking booking = bookingDAO.findById(bookingId);

        if (booking == null) {
            logger.warning(
                    "Booking cancellation failed: Booking not found. ID: "
                            + bookingId
            );

            throw new ValidationException(
                    "Booking not found"
            );
        }

        if ("CANCELLED".equalsIgnoreCase(
                booking.getBookingStatus())) {

            logger.warning(
                    "Booking is already cancelled. Booking ID: "
                            + bookingId
            );

            throw new ValidationException(
                    "Booking is already cancelled"
            );
        }

        bookingDAO.cancelBooking(bookingId);

        logger.info(
                "Booking cancelled successfully. Booking ID: "
                        + bookingId
        );
    }
}