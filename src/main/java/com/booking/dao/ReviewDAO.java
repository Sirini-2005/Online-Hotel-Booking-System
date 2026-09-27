package com.booking.dao;

import com.booking.model.Review;

import java.sql.SQLException;
import java.util.List;

public interface ReviewDAO {

    void save(Review review) throws SQLException;

    Review findById(Long reviewId) throws SQLException;

    List<Review> findAll() throws SQLException;

    List<Review> findByHotelId(Long hotelId) throws SQLException;

    List<Review> findByUserId(Long userId) throws SQLException;

    void update(Review review) throws SQLException;

    void delete(Long reviewId) throws SQLException;
}