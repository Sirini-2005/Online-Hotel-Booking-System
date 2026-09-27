package com.booking.controller;

import com.booking.model.Hotel;

public class HotelControllerTest {

    public static void main(String[] args) {

        try {
            HotelController controller = new HotelController();

            // Create Hotel
            Hotel hotel = new Hotel();

            hotel.setLocationId(null);
            hotel.setName("Grand Hyderabad Hotel");
            hotel.setDescription("A comfortable hotel in Hyderabad");
            hotel.setAddress("Hyderabad, Telangana");
            hotel.setStarRating(4.5);
            hotel.setAmenities("WiFi, Parking, Swimming Pool");
            hotel.setStatus("ACTIVE");

            controller.createHotel(hotel);

            System.out.println("Hotel created successfully!");

            // Display all hotels
            System.out.println("\nAll Hotels:");

            for (Hotel h : controller.getAllHotels()) {
                System.out.println(
                        h.getHotelId() + " | " +
                                h.getName() + " | " +
                                h.getAddress() + " | " +
                                h.getStarRating() + " | " +
                                h.getStatus()
                );
            }

        } catch (Exception e) {
            System.out.println("Error occurred!");
            e.printStackTrace();
        }
    }
}