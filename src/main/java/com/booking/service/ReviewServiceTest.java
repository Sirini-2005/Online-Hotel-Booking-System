package com.booking.service;

import com.booking.model.Review;

public class ReviewServiceTest {

    public static void main(String[] args) {

        try {
            ReviewService reviewService = new ReviewService();

            Review review = new Review();

            review.setUserId(1L);
            review.setHotelId(1L);
            review.setRating(5);
            review.setComment("Excellent hotel and service!");

            reviewService.createReview(review);

            System.out.println("Review created successfully!");

        } catch (Exception e) {

            System.out.println("Review creation failed!");
            System.out.println("Reason: " + e.getMessage());
        }
    }
}