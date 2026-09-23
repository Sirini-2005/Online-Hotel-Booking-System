package com.booking.service;

import com.booking.dao.ReviewDAO;
import com.booking.daoimpl.ReviewDAOImpl;
import com.booking.exception.ValidationException;
import com.booking.model.Review;

import java.sql.SQLException;
import java.util.List;

public class ReviewService {

    private final ReviewDAO reviewDAO;

    public ReviewService() {
        this.reviewDAO = new ReviewDAOImpl();
    }

    public void createReview(Review review) throws SQLException {

        if (review == null) {
            throw new ValidationException("Review cannot be null");
        }

        if (review.getUserId() == null) {
            throw new ValidationException("User ID is required");
        }

        if (review.getHotelId() == null) {
            throw new ValidationException("Hotel ID is required");
        }

        if (review.getRating() == null ||
                review.getRating() < 1 ||
                review.getRating() > 5) {
            throw new ValidationException(
                    "Rating must be between 1 and 5"
            );
        }

        reviewDAO.save(review);
    }

    public Review getReviewById(Long reviewId)
            throws SQLException {
        return reviewDAO.findById(reviewId);
    }

    public List<Review> getAllReviews()
            throws SQLException {
        return reviewDAO.findAll();
    }

    public List<Review> getReviewsByHotelId(Long hotelId)
            throws SQLException {
        return reviewDAO.findByHotelId(hotelId);
    }

    public List<Review> getReviewsByUserId(Long userId)
            throws SQLException {
        return reviewDAO.findByUserId(userId);
    }

    public void updateReview(Review review)
            throws SQLException {
        reviewDAO.update(review);
    }

    public void deleteReview(Long reviewId)
            throws SQLException {
        reviewDAO.delete(reviewId);
    }
}