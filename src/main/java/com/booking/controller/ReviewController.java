package com.booking.controller;

import com.booking.model.Review;
import com.booking.service.ReviewService;

import java.sql.SQLException;
import java.util.List;

public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController() {
        this.reviewService = new ReviewService();
    }

    public void createReview(Review review) throws SQLException {
        reviewService.createReview(review);
    }

    public Review getReviewById(Long reviewId) throws SQLException {
        return reviewService.getReviewById(reviewId);
    }

    public List<Review> getAllReviews() throws SQLException {
        return reviewService.getAllReviews();
    }

    public List<Review> getReviewsByHotelId(Long hotelId) throws SQLException {
        return reviewService.getReviewsByHotelId(hotelId);
    }

    public List<Review> getReviewsByUserId(Long userId) throws SQLException {
        return reviewService.getReviewsByUserId(userId);
    }

    public void updateReview(Review review) throws SQLException {
        reviewService.updateReview(review);
    }

    public void deleteReview(Long reviewId) throws SQLException {
        reviewService.deleteReview(reviewId);
    }
}