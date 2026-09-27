package com.booking.controller;

import com.booking.model.Review;
import com.booking.util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReviewControllerTest {

    public static void main(String[] args) {

        try {
            Long userId = getExistingId("user", "user_id");
            Long hotelId = getExistingId("hotel", "hotel_id");

            if (userId == null || hotelId == null) {
                System.out.println("User or Hotel not found.");
                System.out.println("Create a User and Hotel first.");
                return;
            }

            ReviewController controller = new ReviewController();

            Review review = new Review();

            review.setUserId(userId);
            review.setHotelId(hotelId);
            review.setRating(5);
            review.setComment("Excellent hotel and very comfortable stay.");

            controller.createReview(review);

            System.out.println("Review created successfully!");

            System.out.println("\nAll Reviews:");

            for (Review r : controller.getAllReviews()) {

                System.out.println(
                        r.getReviewId() + " | " +
                                r.getUserId() + " | " +
                                r.getHotelId() + " | " +
                                r.getRating() + " | " +
                                r.getComment()
                );
            }

        } catch (Exception e) {

            System.out.println("Error occurred!");
            e.printStackTrace();
        }
    }

    private static Long getExistingId(
            String tableName,
            String idColumn
    ) throws Exception {

        String sql =
                "SELECT " + idColumn +
                        " FROM `" + tableName + "`" +
                        " ORDER BY " + idColumn +
                        " LIMIT 1";

        try (
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {

            if (resultSet.next()) {
                return resultSet.getLong(idColumn);
            }
        }

        return null;
    }
}