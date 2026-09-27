package com.booking.controller;

import com.booking.model.Location;

public class LocationControllerTest {

    public static void main(String[] args) {

        try {
            LocationController controller = new LocationController();

            // Create Location
            Location location = new Location();

            location.setName("Hyderabad");
            location.setType("CITY");
            location.setParentId(null);

            controller.createLocation(location);

            System.out.println("Location created successfully!");

            // Display all locations
            System.out.println("\nAll Locations:");

            for (Location l : controller.getAllLocations()) {
                System.out.println(
                        l.getLocationId() + " | " +
                                l.getName() + " | " +
                                l.getType() + " | " +
                                l.getParentId()
                );
            }

        } catch (Exception e) {
            System.out.println("Error occurred!");
            e.printStackTrace();
        }
    }
}