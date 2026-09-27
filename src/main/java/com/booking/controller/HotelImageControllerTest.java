package com.booking.controller;

import com.booking.model.HotelImage;
import com.booking.util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HotelImageControllerTest {

    public static void main(String[] args) {

        try {
            Long hotelId = getExistingHotelId();

            if (hotelId == null) {
                System.out.println("No hotel found.");
                System.out.println("Create a hotel first.");
                return;
            }

            HotelImageController controller = new HotelImageController();

            HotelImage hotelImage = new HotelImage();

            hotelImage.setHotelId(hotelId);
            hotelImage.setImageUrl(
                    "https://example.com/hotel-image.jpg"
            );
            hotelImage.setCaption(
                    "Grand Hyderabad Hotel"
            );

            controller.createHotelImage(hotelImage);

            System.out.println("Hotel image created successfully!");

            System.out.println("\nAll Hotel Images:");

            for (HotelImage image : controller.getAllHotelImages()) {

                System.out.println(
                        image.getImageId() + " | " +
                                image.getHotelId() + " | " +
                                image.getImageUrl() + " | " +
                                image.getCaption()
                );
            }

        } catch (Exception e) {

            System.out.println("Error occurred!");
            e.printStackTrace();
        }
    }

    private static Long getExistingHotelId()
            throws Exception {

        String sql =
                "SELECT hotel_id FROM hotel " +
                        "ORDER BY hotel_id LIMIT 1";

        try (
                Connection connection =
                        DBConnection.getConnection();

                Statement statement =
                        connection.createStatement();

                ResultSet resultSet =
                        statement.executeQuery(sql)
        ) {

            if (resultSet.next()) {
                return resultSet.getLong("hotel_id");
            }
        }

        return null;
    }
}