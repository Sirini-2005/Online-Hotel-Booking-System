package com.booking.service;

import com.booking.model.Location;

public class LocationServiceTest {

    public static void main(String[] args) {

        try {

            LocationService locationService = new LocationService();

            Location location = new Location();

            location.setName("Hyderabad");
            location.setType("CITY");
            location.setParentId(null);

            locationService.createLocation(location);

            System.out.println("Location created successfully!");
            System.out.println("Location validation passed.");

        } catch (Exception e) {

            System.out.println("Error occurred!");
            e.printStackTrace();
        }
    }
}