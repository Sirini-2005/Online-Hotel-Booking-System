package com.booking.dao;

import com.booking.model.Hotel;

import java.sql.SQLException;
import java.util.List;

public interface HotelDAO {

    void save(Hotel hotel) throws SQLException;

    Hotel findById(Long hotelId) throws SQLException;

    List<Hotel> findAll() throws SQLException;

    List<Hotel> findByLocationId(Long locationId) throws SQLException;

    void update(Hotel hotel) throws SQLException;

    void delete(Long hotelId) throws SQLException;
}