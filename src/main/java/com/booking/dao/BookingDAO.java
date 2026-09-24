package com.booking.dao;

import com.booking.model.Booking;

import java.sql.SQLException;
import java.util.List;

public interface BookingDAO {

    void save(Booking booking) throws SQLException;

    Booking findById(Long bookingId) throws SQLException;

    List<Booking> findAll() throws SQLException;

    List<Booking> findByUserId(Long userId) throws SQLException;

    List<Booking> findByHotelId(Long hotelId) throws SQLException;

    void update(Booking booking) throws SQLException;

    void delete(Long bookingId) throws SQLException;

    boolean isRoomAvailable(
            Long roomId,
            java.time.LocalDate checkInDate,
            java.time.LocalDate checkOutDate
    ) throws SQLException;

    void cancelBooking(Long bookingId) throws SQLException;
}