package com.booking;

import com.booking.controller.LocationController;
import com.booking.model.Location;

import java.util.List;

public class LocationControllerTest {

    public static void main(String[] args) {

        try {

            LocationController controller = new LocationController();

            // Create Location
            Location location = new Location();

            location.setName("India");
            location.setType("COUNTRY");
            location.setParentId(null);

            controller.createLocation(location);

            System.out.println("Location created successfully!");

            // Get all locations
            List<Location> locations = controller.getAllLocations();

            System.out.println("\nAll Locations:");

            for (Location l : locations) {

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