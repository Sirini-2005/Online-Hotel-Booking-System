package com.booking.daoimpl;

import com.booking.dao.ReviewDAO;
import com.booking.model.Review;
import com.booking.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOImpl implements ReviewDAO {

    private static final Logger logger =
            LoggerFactory.getLogger(ReviewDAOImpl.class);

    @Override
    public void save(Review review) throws SQLException {

        String sql = "INSERT INTO review " +
                "(user_id, hotel_id, rating, comment) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, review.getUserId());
            ps.setLong(2, review.getHotelId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getComment());

            ps.executeUpdate();

            logger.info("Review saved successfully");
        }
    }

    @Override
    public Review findById(Long reviewId) throws SQLException {

        String sql = "SELECT * FROM review WHERE review_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, reviewId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                logger.info("Review found");
                return mapReview(rs);
            }
        }

        logger.warn("Review not found");
        return null;
    }

    @Override
    public List<Review> findAll() throws SQLException {

        String sql = "SELECT * FROM review";

        List<Review> reviews = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                reviews.add(mapReview(rs));
            }
        }

        logger.info("All reviews fetched");

        return reviews;
    }

    @Override
    public List<Review> findByHotelId(Long hotelId)
            throws SQLException {

        String sql = "SELECT * FROM review WHERE hotel_id = ?";

        List<Review> reviews = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, hotelId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                reviews.add(mapReview(rs));
            }
        }

        logger.info("Reviews fetched by hotel");

        return reviews;
    }

    @Override
    public List<Review> findByUserId(Long userId)
            throws SQLException {

        String sql = "SELECT * FROM review WHERE user_id = ?";

        List<Review> reviews = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                reviews.add(mapReview(rs));
            }
        }

        logger.info("Reviews fetched by user");

        return reviews;
    }

    @Override
    public void update(Review review) throws SQLException {

        String sql = "UPDATE review SET " +
                "user_id = ?, hotel_id = ?, rating = ?, comment = ? " +
                "WHERE review_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, review.getUserId());
            ps.setLong(2, review.getHotelId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getComment());
            ps.setLong(5, review.getReviewId());

            ps.executeUpdate();

            logger.info("Review updated successfully");
        }
    }

    @Override
    public void delete(Long reviewId) throws SQLException {

        String sql = "DELETE FROM review WHERE review_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, reviewId);

            ps.executeUpdate();

            logger.info("Review deleted successfully");
        }
    }

    private Review mapReview(ResultSet rs) throws SQLException {

        Review review = new Review();

        review.setReviewId(rs.getLong("review_id"));
        review.setUserId(rs.getLong("user_id"));
        review.setHotelId(rs.getLong("hotel_id"));
        review.setRating(rs.getInt("rating"));
        review.setComment(rs.getString("comment"));

        return review;
    }
}