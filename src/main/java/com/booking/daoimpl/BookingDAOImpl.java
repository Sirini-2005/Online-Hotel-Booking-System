package com.booking.daoimpl;

import com.booking.dao.BookingDAO;
import com.booking.model.Booking;
import com.booking.model.BookingDetails;
import com.booking.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    private static final Logger logger =
            LoggerFactory.getLogger(BookingDAOImpl.class);

    @Override
    public void save(Booking booking) throws SQLException {

        String sql = """
                INSERT INTO booking
                (
                    user_id,
                    hotel_id,
                    room_id,
                    check_in_date,
                    check_out_date,
                    guests,
                    total_amount,
                    booking_status
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            statement.setLong(
                    1,
                    booking.getUserId()
            );

            statement.setLong(
                    2,
                    booking.getHotelId()
            );

            statement.setLong(
                    3,
                    booking.getRoomId()
            );

            statement.setDate(
                    4,
                    Date.valueOf(
                            booking.getCheckInDate()
                    )
            );

            statement.setDate(
                    5,
                    Date.valueOf(
                            booking.getCheckOutDate()
                    )
            );

            statement.setInt(
                    6,
                    booking.getGuests()
            );

            statement.setDouble(
                    7,
                    booking.getTotalAmount()
            );

            statement.setString(
                    8,
                    booking.getBookingStatus()
            );

            statement.executeUpdate();

            try (ResultSet rs =
                         statement.getGeneratedKeys()) {

                if (rs.next()) {

                    booking.setBookingId(
                            rs.getLong(1)
                    );
                }
            }

            logger.info(
                    "Booking saved successfully"
            );
        }
    }

    @Override
    public Booking findById(Long bookingId)
            throws SQLException {

        String sql = """
                SELECT
                    booking_id,
                    user_id,
                    hotel_id,
                    room_id,
                    check_in_date,
                    check_out_date,
                    guests,
                    total_amount,
                    booking_status
                FROM booking
                WHERE booking_id = ?
                """;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(
                    1,
                    bookingId
            );

            try (ResultSet rs =
                         statement.executeQuery()) {

                if (rs.next()) {

                    return mapBooking(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Booking> findAll()
            throws SQLException {

        List<Booking> bookings =
                new ArrayList<>();

        String sql = """
                SELECT
                    booking_id,
                    user_id,
                    hotel_id,
                    room_id,
                    check_in_date,
                    check_out_date,
                    guests,
                    total_amount,
                    booking_status
                FROM booking
                ORDER BY booking_id
                """;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet rs =
                        statement.executeQuery()
        ) {

            while (rs.next()) {

                bookings.add(
                        mapBooking(rs)
                );
            }
        }

        return bookings;
    }

    @Override
    public List<Booking> findByUserId(
            Long userId)
            throws SQLException {

        List<Booking> bookings =
                new ArrayList<>();

        String sql = """
                SELECT
                    booking_id,
                    user_id,
                    hotel_id,
                    room_id,
                    check_in_date,
                    check_out_date,
                    guests,
                    total_amount,
                    booking_status
                FROM booking
                WHERE user_id = ?
                ORDER BY booking_id
                """;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(
                    1,
                    userId
            );

            try (ResultSet rs =
                         statement.executeQuery()) {

                while (rs.next()) {

                    bookings.add(
                            mapBooking(rs)
                    );
                }
            }
        }

        return bookings;
    }

    @Override
    public List<Booking> findByHotelId(
            Long hotelId)
            throws SQLException {

        List<Booking> bookings =
                new ArrayList<>();

        String sql = """
                SELECT
                    booking_id,
                    user_id,
                    hotel_id,
                    room_id,
                    check_in_date,
                    check_out_date,
                    guests,
                    total_amount,
                    booking_status
                FROM booking
                WHERE hotel_id = ?
                ORDER BY booking_id
                """;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(
                    1,
                    hotelId
            );

            try (ResultSet rs =
                         statement.executeQuery()) {

                while (rs.next()) {

                    bookings.add(
                            mapBooking(rs)
                    );
                }
            }
        }

        return bookings;
    }

    @Override
    public boolean isRoomAvailable(
            Long roomId,
            LocalDate checkInDate,
            LocalDate checkOutDate)
            throws SQLException {

        String sql = """
                SELECT COUNT(*)
                FROM booking
                WHERE room_id = ?
                AND booking_status
                    NOT IN ('CANCELLED', 'REJECTED')
                AND check_in_date < ?
                AND check_out_date > ?
                """;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(
                    1,
                    roomId
            );

            statement.setDate(
                    2,
                    Date.valueOf(checkOutDate)
            );

            statement.setDate(
                    3,
                    Date.valueOf(checkInDate)
            );

            try (ResultSet rs =
                         statement.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(1) == 0;
                }
            }
        }

        return false;
    }

    @Override
    public void cancelBooking(
            Long bookingId)
            throws SQLException {

        String sql = """
                UPDATE booking
                SET booking_status = 'CANCELLED'
                WHERE booking_id = ?
                """;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(
                    1,
                    bookingId
            );

            statement.executeUpdate();

            logger.info(
                    "Booking cancelled successfully"
            );
        }
    }

    @Override
    public void update(
            Booking booking)
            throws SQLException {

        String sql = """
                UPDATE booking
                SET
                    check_in_date = ?,
                    check_out_date = ?,
                    guests = ?,
                    total_amount = ?,
                    booking_status = ?
                WHERE booking_id = ?
                """;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setDate(
                    1,
                    Date.valueOf(
                            booking.getCheckInDate()
                    )
            );

            statement.setDate(
                    2,
                    Date.valueOf(
                            booking.getCheckOutDate()
                    )
            );

            statement.setInt(
                    3,
                    booking.getGuests()
            );

            statement.setDouble(
                    4,
                    booking.getTotalAmount()
            );

            statement.setString(
                    5,
                    booking.getBookingStatus()
            );

            statement.setLong(
                    6,
                    booking.getBookingId()
            );

            statement.executeUpdate();
        }
    }

    @Override
    public void delete(
            Long bookingId)
            throws SQLException {

        String sql = """
                DELETE FROM booking
                WHERE booking_id = ?
                """;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(
                    1,
                    bookingId
            );

            statement.executeUpdate();
        }
    }

    @Override
    public List<BookingDetails>
    findAllBookingDetails()
            throws SQLException {

        List<BookingDetails> list =
                new ArrayList<>();

        String sql = """
                SELECT
                    b.booking_id,
                    u.full_name,
                    u.email,
                    h.name AS hotel_name,
                    r.room_number,
                    r.room_type,
                    b.check_in_date,
                    b.check_out_date,
                    b.guests,
                    b.total_amount,
                    b.booking_status
                FROM booking b
                JOIN user u
                    ON b.user_id = u.user_id
                JOIN hotel h
                    ON b.hotel_id = h.hotel_id
                JOIN room r
                    ON b.room_id = r.room_id
                ORDER BY b.booking_id
                """;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet rs =
                        statement.executeQuery()
        ) {

            while (rs.next()) {

                BookingDetails details =
                        new BookingDetails();

                details.setBookingId(
                        rs.getLong("booking_id")
                );

                details.setUserName(
                        rs.getString("full_name")
                );

                details.setUserEmail(
                        rs.getString("email")
                );

                details.setHotelName(
                        rs.getString("hotel_name")
                );

                details.setRoomNumber(
                        rs.getString("room_number")
                );

                details.setRoomType(
                        rs.getString("room_type")
                );

                details.setCheckInDate(
                        rs.getDate("check_in_date")
                                .toLocalDate()
                );

                details.setCheckOutDate(
                        rs.getDate("check_out_date")
                                .toLocalDate()
                );

                details.setGuests(
                        rs.getInt("guests")
                );

                details.setTotalAmount(
                        rs.getDouble("total_amount")
                );

                details.setBookingStatus(
                        rs.getString("booking_status")
                );

                list.add(details);
            }
        }

        logger.info(
                "Booking details fetched using JOIN"
        );

        return list;
    }

    private Booking mapBooking(
            ResultSet rs)
            throws SQLException {

        Booking booking =
                new Booking();

        booking.setBookingId(
                rs.getLong("booking_id")
        );

        booking.setUserId(
                rs.getLong("user_id")
        );

        booking.setHotelId(
                rs.getLong("hotel_id")
        );

        booking.setRoomId(
                rs.getLong("room_id")
        );

        booking.setCheckInDate(
                rs.getDate(
                        "check_in_date"
                ).toLocalDate()
        );

        booking.setCheckOutDate(
                rs.getDate(
                        "check_out_date"
                ).toLocalDate()
        );

        booking.setGuests(
                rs.getInt("guests")
        );

        booking.setTotalAmount(
                rs.getDouble("total_amount")
        );

        booking.setBookingStatus(
                rs.getString(
                        "booking_status"
                )
        );

        return booking;
    }
}