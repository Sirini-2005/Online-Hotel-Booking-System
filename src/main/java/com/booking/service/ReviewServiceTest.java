package com.booking.service;

import com.booking.model.Review;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.booking.util.DBConnection;

public class ReviewServiceTest {

    public static void main(String[] args) {

        try {

            ReviewService reviewService = new ReviewService();

            Long userId = getExistingId("user", "user_id");
            Long hotelId = getExistingId("hotel", "hotel_id");

            if (userId == null || hotelId == null) {
                System.out.println("User or Hotel not found.");
                System.out.println("Please create a User and Hotel first.");
                return;
            }

            Review review = new Review();

            review.setUserId(userId);
            review.setHotelId(hotelId);
            review.setRating(5);
            review.setComment(
                    "Excellent hotel and comfortable stay."
            );

            reviewService.createReview(review);

            System.out.println("Review created successfully!");
            System.out.println("Review validation passed.");

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
                Connection connection =
                        DBConnection.getConnection();

                Statement statement =
                        connection.createStatement();

                ResultSet resultSet =
                        statement.executeQuery(sql)
        ) {

            if (resultSet.next()) {
                return resultSet.getLong(idColumn);
            }
        }

        return null;
    }
}