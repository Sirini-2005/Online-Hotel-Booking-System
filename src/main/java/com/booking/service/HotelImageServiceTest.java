package com.booking.service;

import com.booking.model.HotelImage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.booking.util.DBConnection;

public class HotelImageServiceTest {

    public static void main(String[] args) {

        try {

            HotelImageService hotelImageService =
                    new HotelImageService();

            Long hotelId = getExistingHotelId();

            if (hotelId == null) {
                System.out.println("No hotel found.");
                System.out.println("Please create a hotel first.");
                return;
            }

            HotelImage hotelImage = new HotelImage();

            hotelImage.setHotelId(hotelId);
            hotelImage.setImageUrl(
                    "https://example.com/hotel-image.jpg"
            );
            hotelImage.setCaption(
                    "Test Hotel Image"
            );

            hotelImageService.createHotelImage(hotelImage);

            System.out.println(
                    "Hotel image created successfully!"
            );

            System.out.println(
                    "Hotel image validation passed."
            );

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