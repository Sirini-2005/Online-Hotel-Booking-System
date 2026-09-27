package com.booking.controller;

import com.booking.model.Location;
import com.booking.service.LocationService;

import java.sql.SQLException;
import java.util.List;

public class LocationController {

    private final LocationService locationService;

    public LocationController() {
        this.locationService = new LocationService();
    }

    public void createLocation(Location location) throws SQLException {
        locationService.createLocation(location);
    }

    public Location getLocationById(Long locationId) throws SQLException {
        return locationService.getLocationById(locationId);
    }

    public List<Location> getAllLocations() throws SQLException {
        return locationService.getAllLocations();
    }

    public List<Location> getLocationsByType(String type) throws SQLException {
        return locationService.getLocationsByType(type);
    }

    public void updateLocation(Location location) throws SQLException {
        locationService.updateLocation(location);
    }

    public void deleteLocation(Long locationId) throws SQLException {
        locationService.deleteLocation(locationId);
    }
}