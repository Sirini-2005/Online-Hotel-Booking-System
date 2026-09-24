package com.booking.daoimpl;

import com.booking.dao.BookingDAO;
import com.booking.model.Booking;
import com.booking.util.DBConnection;
import com.booking.util.LoggerUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BookingDAOImpl implements BookingDAO {

    private static final Logger logger =
            LoggerUtil.getLogger(BookingDAOImpl.class);

    @Override
    public void save(Booking booking) throws SQLException {

        String sql = """
                INSERT INTO booking
                (user_id, hotel_id, room_id, check_in_date, check_out_date,
                 guests, total_amount, booking_status)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, booking.getUserId());
            statement.setLong(2, booking.getHotelId());
            statement.setLong(3, booking.getRoomId());
            statement.setDate(
                    4,
                    Date.valueOf(booking.getCheckInDate())
            );
            statement.setDate(
                    5,
                    Date.valueOf(booking.getCheckOutDate())
            );
            statement.setInt(6, booking.getGuests());
            statement.setDouble(7, booking.getTotalAmount());
            statement.setString(8, booking.getBookingStatus());

            statement.executeUpdate();

            logger.info(
                    "Booking saved successfully. Booking user ID: "
                            + booking.getUserId()
            );
        }
    }

    @Override
    public Booking findById(Long bookingId) throws SQLException {

        String sql =
                "SELECT * FROM booking WHERE booking_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, bookingId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    logger.info(
                            "Booking found. Booking ID: "
                                    + bookingId
                    );

                    return mapResultSetToBooking(resultSet);
                }
            }
        }

        logger.warning(
                "Booking not found. Booking ID: "
                        + bookingId
        );

        return null;
    }

    @Override
    public List<Booking> findAll() throws SQLException {

        String sql = "SELECT * FROM booking";

        List<Booking> bookings = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {
                bookings.add(
                        mapResultSetToBooking(resultSet)
                );
            }
        }

        logger.info(
                "All bookings retrieved. Count: "
                        + bookings.size()
        );

        return bookings;
    }

    @Override
    public List<Booking> findByUserId(Long userId)
            throws SQLException {

        String sql =
                "SELECT * FROM booking WHERE user_id = ?";

        List<Booking> bookings = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, userId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {
                    bookings.add(
                            mapResultSetToBooking(resultSet)
                    );
                }
            }
        }

        logger.info(
                "Bookings retrieved for User ID: "
                        + userId
        );

        return bookings;
    }

    @Override
    public List<Booking> findByHotelId(Long hotelId)
            throws SQLException {

        String sql =
                "SELECT * FROM booking WHERE hotel_id = ?";

        List<Booking> bookings = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, hotelId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {
                    bookings.add(
                            mapResultSetToBooking(resultSet)
                    );
                }
            }
        }

        logger.info(
                "Bookings retrieved for Hotel ID: "
                        + hotelId
        );

        return bookings;
    }

    @Override
    public void update(Booking booking) throws SQLException {

        String sql = """
                UPDATE booking
                SET user_id = ?,
                    hotel_id = ?,
                    room_id = ?,
                    check_in_date = ?,
                    check_out_date = ?,
                    guests = ?,
                    total_amount = ?,
                    booking_status = ?
                WHERE booking_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, booking.getUserId());
            statement.setLong(2, booking.getHotelId());
            statement.setLong(3, booking.getRoomId());
            statement.setDate(
                    4,
                    Date.valueOf(booking.getCheckInDate())
            );
            statement.setDate(
                    5,
                    Date.valueOf(booking.getCheckOutDate())
            );
            statement.setInt(6, booking.getGuests());
            statement.setDouble(7, booking.getTotalAmount());
            statement.setString(8, booking.getBookingStatus());
            statement.setLong(9, booking.getBookingId());

            statement.executeUpdate();

            logger.info(
                    "Booking updated successfully. Booking ID: "
                            + booking.getBookingId()
            );
        }
    }

    @Override
    public void delete(Long bookingId) throws SQLException {

        String sql =
                "DELETE FROM booking WHERE booking_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, bookingId);

            statement.executeUpdate();

            logger.info(
                    "Booking deleted successfully. Booking ID: "
                            + bookingId
            );
        }
    }

    @Override
    public boolean isRoomAvailable(
            Long roomId,
            java.time.LocalDate checkInDate,
            java.time.LocalDate checkOutDate
    ) throws SQLException {

        String sql = """
                SELECT COUNT(*)
                FROM booking
                WHERE room_id = ?
                  AND booking_status <> 'CANCELLED'
                  AND check_in_date < ?
                  AND check_out_date > ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, roomId);
            statement.setDate(
                    2,
                    Date.valueOf(checkOutDate)
            );
            statement.setDate(
                    3,
                    Date.valueOf(checkInDate)
            );

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    boolean available =
                            resultSet.getInt(1) == 0;

                    logger.info(
                            "Room availability checked. Room ID: "
                                    + roomId
                                    + ", Available: "
                                    + available
                    );

                    return available;
                }
            }
        }

        return false;
    }

    @Override
    public void cancelBooking(Long bookingId)
            throws SQLException {

        String sql = """
                UPDATE booking
                SET booking_status = 'CANCELLED'
                WHERE booking_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, bookingId);

            statement.executeUpdate();

            logger.info(
                    "Booking cancelled successfully. Booking ID: "
                            + bookingId
            );
        }
    }

    private Booking mapResultSetToBooking(
            ResultSet resultSet) throws SQLException {

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
                resultSet.getDate("check_in_date")
                        .toLocalDate()
        );

        booking.setCheckOutDate(
                resultSet.getDate("check_out_date")
                        .toLocalDate()
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