package com.booking.service;

import com.booking.dao.ReviewDAO;
import com.booking.daoimpl.ReviewDAOImpl;
import com.booking.exception.ValidationException;
import com.booking.model.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class ReviewService {

    private static final Logger logger =
            LoggerFactory.getLogger(ReviewService.class);

    private final ReviewDAO reviewDAO;

    public ReviewService() {
        this.reviewDAO = new ReviewDAOImpl();
    }

    public void createReview(Review review) throws SQLException {

        logger.info("Creating review");

        if (review == null) {
            logger.warn("Review is null");
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

        logger.info("Review created successfully");
    }

    public Review getReviewById(Long reviewId)
            throws SQLException {

        logger.info("Getting review by ID");

        return reviewDAO.findById(reviewId);
    }

    public List<Review> getAllReviews()
            throws SQLException {

        logger.info("Getting all reviews");

        return reviewDAO.findAll();
    }

    public List<Review> getReviewsByHotelId(Long hotelId)
            throws SQLException {

        logger.info("Getting reviews by hotel");

        return reviewDAO.findByHotelId(hotelId);
    }

    public List<Review> getReviewsByUserId(Long userId)
            throws SQLException {

        logger.info("Getting reviews by user");

        return reviewDAO.findByUserId(userId);
    }

    public void updateReview(Review review)
            throws SQLException {

        logger.info("Updating review");

        reviewDAO.update(review);

        logger.info("Review updated successfully");
    }

    public void deleteReview(Long reviewId)
            throws SQLException {

        logger.info("Deleting review");

        reviewDAO.delete(reviewId);

        logger.info("Review deleted successfully");
    }
}