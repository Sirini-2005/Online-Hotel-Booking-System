package com.booking.daoimpl;

import com.booking.dao.BookingDAO;
import com.booking.model.Booking;
import com.booking.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    private static final Logger logger =
            LoggerFactory.getLogger(BookingDAOImpl.class);

    @Override
    public void save(Booking booking) throws SQLException {

        String sql = "INSERT INTO booking " +
                "(user_id, hotel_id, room_id, check_in_date, check_out_date, " +
                "guests, total_amount, booking_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        logger.info("Creating booking for room ID: {}", booking.getRoomId());

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, booking.getUserId());
            statement.setLong(2, booking.getHotelId());
            statement.setLong(3, booking.getRoomId());
            statement.setDate(4,
                    java.sql.Date.valueOf(booking.getCheckInDate()));
            statement.setDate(5,
                    java.sql.Date.valueOf(booking.getCheckOutDate()));
            statement.setInt(6, booking.getGuests());
            statement.setDouble(7, booking.getTotalAmount());
            statement.setString(8, booking.getBookingStatus());

            statement.executeUpdate();

            logger.info("Booking created successfully");
        } catch (SQLException e) {
            logger.error("Error while creating booking", e);
            throw e;
        }
    }

    @Override
    public Booking findById(Long bookingId) throws SQLException {

        String sql = "SELECT * FROM booking WHERE booking_id = ?";

        logger.info("Finding booking with ID: {}", bookingId);

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, bookingId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    logger.info("Booking found with ID: {}", bookingId);
                    return mapBooking(resultSet);
                }
            }

        } catch (SQLException e) {
            logger.error(
                    "Error while finding booking with ID: {}",
                    bookingId,
                    e
            );
            throw e;
        }

        logger.warn("Booking not found with ID: {}", bookingId);
        return null;
    }

    @Override
    public List<Booking> findAll() throws SQLException {

        String sql = "SELECT * FROM booking";

        logger.info("Fetching all bookings");

        List<Booking> bookings = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                bookings.add(mapBooking(resultSet));
            }

            logger.info("Fetched {} bookings", bookings.size());

        } catch (SQLException e) {
            logger.error("Error while fetching all bookings", e);
            throw e;
        }

        return bookings;
    }

    @Override
    public List<Booking> findByUserId(Long userId) throws SQLException {

        String sql = "SELECT * FROM booking WHERE user_id = ?";

        logger.info("Finding bookings for user ID: {}", userId);

        List<Booking> bookings = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    bookings.add(mapBooking(resultSet));
                }
            }

            logger.info(
                    "Found {} bookings for user ID: {}",
                    bookings.size(),
                    userId
            );

        } catch (SQLException e) {
            logger.error(
                    "Error while finding bookings for user ID: {}",
                    userId,
                    e
            );
            throw e;
        }

        return bookings;
    }

    @Override
    public List<Booking> findByHotelId(Long hotelId) throws SQLException {

        String sql = "SELECT * FROM booking WHERE hotel_id = ?";

        logger.info("Finding bookings for hotel ID: {}", hotelId);

        List<Booking> bookings = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, hotelId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    bookings.add(mapBooking(resultSet));
                }
            }

            logger.info(
                    "Found {} bookings for hotel ID: {}",
                    bookings.size(),
                    hotelId
            );

        } catch (SQLException e) {
            logger.error(
                    "Error while finding bookings for hotel ID: {}",
                    hotelId,
                    e
            );
            throw e;
        }

        return bookings;
    }

    @Override
    public boolean isRoomAvailable(
            Long roomId,
            LocalDate checkInDate,
            LocalDate checkOutDate) throws SQLException {

        String sql = "SELECT COUNT(*) FROM booking " +
                "WHERE room_id = ? " +
                "AND booking_status NOT IN ('CANCELLED', 'REJECTED') " +
                "AND check_in_date < ? " +
                "AND check_out_date > ?";

        logger.info(
                "Checking room availability. Room ID: {}, Check-in: {}, Check-out: {}",
                roomId,
                checkInDate,
                checkOutDate
        );

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, roomId);
            statement.setDate(
                    2,
                    java.sql.Date.valueOf(checkOutDate)
            );
            statement.setDate(
                    3,
                    java.sql.Date.valueOf(checkInDate)
            );

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    int bookingCount = resultSet.getInt(1);

                    boolean available = bookingCount == 0;

                    logger.info(
                            "Room ID: {} availability: {}",
                            roomId,
                            available
                    );

                    return available;
                }
            }

        } catch (SQLException e) {
            logger.error(
                    "Error while checking room availability for room ID: {}",
                    roomId,
                    e
            );
            throw e;
        }

        return false;
    }

    @Override
    public void cancelBooking(Long bookingId) throws SQLException {

        String sql = "UPDATE booking " +
                "SET booking_status = ? " +
                "WHERE booking_id = ?";

        logger.info("Cancelling booking with ID: {}", bookingId);

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "CANCELLED");
            statement.setLong(2, bookingId);

            statement.executeUpdate();

            logger.info(
                    "Booking cancelled successfully with ID: {}",
                    bookingId
            );

        } catch (SQLException e) {
            logger.error(
                    "Error while cancelling booking with ID: {}",
                    bookingId,
                    e
            );
            throw e;
        }
    }

    @Override
    public void update(Booking booking) throws SQLException {

        String sql = "UPDATE booking SET " +
                "user_id = ?, " +
                "hotel_id = ?, " +
                "room_id = ?, " +
                "check_in_date = ?, " +
                "check_out_date = ?, " +
                "guests = ?, " +
                "total_amount = ?, " +
                "booking_status = ? " +
                "WHERE booking_id = ?";

        logger.info(
                "Updating booking with ID: {}",
                booking.getBookingId()
        );

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, booking.getUserId());
            statement.setLong(2, booking.getHotelId());
            statement.setLong(3, booking.getRoomId());
            statement.setDate(
                    4,
                    java.sql.Date.valueOf(booking.getCheckInDate())
            );
            statement.setDate(
                    5,
                    java.sql.Date.valueOf(booking.getCheckOutDate())
            );
            statement.setInt(6, booking.getGuests());
            statement.setDouble(7, booking.getTotalAmount());
            statement.setString(8, booking.getBookingStatus());
            statement.setLong(9, booking.getBookingId());

            statement.executeUpdate();

            logger.info(
                    "Booking updated successfully with ID: {}",
                    booking.getBookingId()
            );

        } catch (SQLException e) {
            logger.error(
                    "Error while updating booking with ID: {}",
                    booking.getBookingId(),
                    e
            );
            throw e;
        }
    }

    @Override
    public void delete(Long bookingId) throws SQLException {

        String sql = "DELETE FROM booking WHERE booking_id = ?";

        logger.info("Deleting booking with ID: {}", bookingId);

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, bookingId);

            statement.executeUpdate();

            logger.info(
                    "Booking deleted successfully with ID: {}",
                    bookingId
            );

        } catch (SQLException e) {
            logger.error(
                    "Error while deleting booking with ID: {}",
                    bookingId,
                    e
            );
            throw e;
        }
    }

    private Booking mapBooking(ResultSet resultSet) throws SQLException {

        Booking booking = new Booking();

        booking.setBookingId(
                resultSet.getLong("booking_id")
        );

        booking.setUserId(
                resultSet.getLong("user_id")
        );

        booking.setHotelId(
                resultSet.getLong("hotel_id")
        );

        booking.setRoomId(
                resultSet.getLong("room_id")
        );

        booking.setCheckInDate(
                resultSet.getDate("check_in_date").toLocalDate()
        );

        booking.setCheckOutDate(
                resultSet.getDate("check_out_date").toLocalDate()
        );

        booking.setGuests(
                resultSet.getInt("guests")
        );

        booking.setTotalAmount(
                resultSet.getDouble("total_amount")
        );

        booking.setBookingStatus(
                resultSet.getString("booking_status")
        );

        return booking;
    }
}