package com.booking.service;

import com.booking.exception.ValidationException;
import com.booking.model.Review;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ReviewServiceJUnitTest {

    @Test
    void shouldRejectReviewWhenUserIdIsMissing() {

        ReviewService reviewService = new ReviewService();

        Review review = new Review();
        review.setUserId(null);
        review.setHotelId(1L);
        review.setRating(5);
        review.setComment("Excellent hotel");

        assertThrows(
                ValidationException.class,
                () -> reviewService.createReview(review)
        );
    }

    @Test
    void shouldRejectReviewWhenHotelIdIsMissing() {

        ReviewService reviewService = new ReviewService();

        Review review = new Review();
        review.setUserId(1L);
        review.setHotelId(null);
        review.setRating(5);
        review.setComment("Excellent hotel");

        assertThrows(
                ValidationException.class,
                () -> reviewService.createReview(review)
        );
    }

    @Test
    void shouldRejectReviewWhenRatingIsInvalid() {

        ReviewService reviewService = new ReviewService();

        Review review = new Review();
        review.setUserId(1L);
        review.setHotelId(1L);
        review.setRating(6);
        review.setComment("Excellent hotel");

        assertThrows(
                ValidationException.class,
                () -> reviewService.createReview(review)
        );
    }
}