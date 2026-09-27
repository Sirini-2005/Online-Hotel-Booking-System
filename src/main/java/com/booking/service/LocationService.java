package com.booking.service;

import com.booking.dao.LocationDAO;
import com.booking.daoimpl.LocationDAOImpl;
import com.booking.exception.ValidationException;
import com.booking.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class LocationService {

    private static final Logger logger =
            LoggerFactory.getLogger(LocationService.class);

    private final LocationDAO locationDAO;

    public LocationService() {
        this.locationDAO = new LocationDAOImpl();
    }

    public void createLocation(Location location) throws SQLException {

        logger.info("Creating location");

        if (location == null) {
            logger.warn("Location is null");
            throw new ValidationException("Location cannot be null");
        }

        if (location.getName() == null ||
                location.getName().trim().isEmpty()) {

            logger.warn("Location name is missing");
            throw new ValidationException("Location name is required");
        }

        if (location.getType() == null ||
                location.getType().trim().isEmpty()) {

            logger.warn("Location type is missing");
            throw new ValidationException("Location type is required");
        }

        locationDAO.save(location);

        logger.info("Location created successfully");
    }

    public Location getLocationById(Long locationId)
            throws SQLException {

        logger.info("Getting location by ID");

        return locationDAO.findById(locationId);
    }

    public List<Location> getAllLocations()
            throws SQLException {

        logger.info("Getting all locations");

        return locationDAO.findAll();
    }

    public List<Location> getLocationsByType(String type)
            throws SQLException {

        logger.info("Getting locations by type");

        return locationDAO.findByType(type);
    }

    public void updateLocation(Location location)
            throws SQLException {

        logger.info("Updating location");

        locationDAO.update(location);

        logger.info("Location updated successfully");
    }

    public void deleteLocation(Long locationId)
            throws SQLException {

        logger.info("Deleting location");

        locationDAO.delete(locationId);

        logger.info("Location deleted successfully");
    }
}