package com.booking.dao;

import com.booking.model.Booking;
import com.booking.model.BookingDetails;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface BookingDAO {

    void save(Booking booking) throws SQLException;

    Booking findById(Long bookingId) throws SQLException;

    List<Booking> findAll() throws SQLException;

    List<Booking> findByUserId(Long userId) throws SQLException;

    List<Booking> findByHotelId(Long hotelId) throws SQLException;

    boolean isRoomAvailable(
            Long roomId,
            LocalDate checkIn,
            LocalDate checkOut
    ) throws SQLException;

    void update(Booking booking) throws SQLException;

    void cancelBooking(Long bookingId) throws SQLException;

    void delete(Long bookingId) throws SQLException;

    List<BookingDetails> findAllBookingDetails() throws SQLException;
}