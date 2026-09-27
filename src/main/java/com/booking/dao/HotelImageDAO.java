package com.booking.dao;

import com.booking.model.HotelImage;

import java.sql.SQLException;
import java.util.List;

public interface HotelImageDAO {

    void save(HotelImage hotelImage) throws SQLException;

    HotelImage findById(Long imageId) throws SQLException;

    List<HotelImage> findAll() throws SQLException;

    List<HotelImage> findByHotelId(Long hotelId) throws SQLException;

    void update(HotelImage hotelImage) throws SQLException;

    void delete(Long imageId) throws SQLException;
}