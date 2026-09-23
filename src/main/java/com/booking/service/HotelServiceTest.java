package com.booking.service;

import com.booking.model.Hotel;

public class HotelServiceTest {

    public static void main(String[] args) {

        try {

            HotelService hotelService = new HotelService();

            Hotel hotel = new Hotel();

            hotel.setLocationId(null);
            hotel.setName("Test Hyderabad Hotel");
            hotel.setDescription(
                    "A test hotel for service validation"
            );
            hotel.setAddress("Hyderabad, Telangana");
            hotel.setStarRating(4.0);
            hotel.setAmenities("WiFi, Parking");
            hotel.setStatus("ACTIVE");

            hotelService.createHotel(hotel);

            System.out.println("Hotel created successfully!");
            System.out.println("Hotel validation passed.");

        } catch (Exception e) {

            System.out.println("Error occurred!");
            e.printStackTrace();
        }
    }
}