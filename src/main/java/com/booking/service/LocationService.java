package com.booking.service;

import com.booking.dao.LocationDAO;
import com.booking.daoimpl.LocationDAOImpl;
import com.booking.exception.ValidationException;
import com.booking.model.Location;

import java.sql.SQLException;
import java.util.List;

public class LocationService {

    private final LocationDAO locationDAO;

    public LocationService() {
        this.locationDAO = new LocationDAOImpl();
    }

    public void createLocation(Location location) throws SQLException {

        if (location == null) {
            throw new ValidationException("Location cannot be null");
        }

        if (location.getName() == null ||
                location.getName().trim().isEmpty()) {
            throw new ValidationException("Location name is required");
        }

        if (location.getType() == null ||
                location.getType().trim().isEmpty()) {
            throw new ValidationException("Location type is required");
        }

        locationDAO.save(location);
    }

    public Location getLocationById(Long locationId)
            throws SQLException {
        return locationDAO.findById(locationId);
    }

    public List<Location> getAllLocations()
            throws SQLException {
        return locationDAO.findAll();
    }

    public List<Location> getLocationsByType(String type)
            throws SQLException {
        return locationDAO.findByType(type);
    }

    public void updateLocation(Location location)
            throws SQLException {
        locationDAO.update(location);
    }

    public void deleteLocation(Long locationId)
            throws SQLException {
        locationDAO.delete(locationId);
    }
}