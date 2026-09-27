package com.booking.service;

import com.booking.dao.HotelDAO;
import com.booking.daoimpl.HotelDAOImpl;
import com.booking.exception.ValidationException;
import com.booking.model.Hotel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class HotelService {

    private static final Logger logger =
            LoggerFactory.getLogger(HotelService.class);

    private final HotelDAO hotelDAO;

    public HotelService() {
        this.hotelDAO = new HotelDAOImpl();
    }

    public void createHotel(Hotel hotel) throws SQLException {

        logger.info("Creating hotel");

        if (hotel == null) {
            logger.warn("Hotel is null");
            throw new ValidationException("Hotel cannot be null");
        }

        if (hotel.getName() == null ||
                hotel.getName().trim().isEmpty()) {

            logger.warn("Hotel name is missing");
            throw new ValidationException("Hotel name is required");
        }

        if (hotel.getAddress() == null ||
                hotel.getAddress().trim().isEmpty()) {

            logger.warn("Hotel address is missing");
            throw new ValidationException("Hotel address is required");
        }

        if (hotel.getStarRating() != null &&
                (hotel.getStarRating() < 0 ||
                        hotel.getStarRating() > 5)) {

            logger.warn("Invalid hotel star rating");
            throw new ValidationException(
                    "Star rating must be between 0 and 5"
            );
        }

        if (hotel.getStatus() == null ||
                hotel.getStatus().trim().isEmpty()) {

            logger.warn("Hotel status is missing");
            throw new ValidationException("Hotel status is required");
        }

        hotelDAO.save(hotel);

        logger.info("Hotel created successfully");
    }

    public Hotel getHotelById(Long hotelId)
            throws SQLException {

        logger.info("Getting hotel by ID");

        return hotelDAO.findById(hotelId);
    }

    public List<Hotel> getAllHotels()
            throws SQLException {

        logger.info("Getting all hotels");

        return hotelDAO.findAll();
    }

    public List<Hotel> getHotelsByLocationId(Long locationId)
            throws SQLException {

        logger.info("Getting hotels by location");

        return hotelDAO.findByLocationId(locationId);
    }

    public void updateHotel(Hotel hotel)
            throws SQLException {

        logger.info("Updating hotel");

        hotelDAO.update(hotel);

        logger.info("Hotel updated successfully");
    }

    public void deleteHotel(Long hotelId)
            throws SQLException {

        logger.info("Deleting hotel");

        hotelDAO.delete(hotelId);

        logger.info("Hotel deleted successfully");
    }
}