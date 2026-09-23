package com.booking.service;

import com.booking.dao.HotelDAO;
import com.booking.daoimpl.HotelDAOImpl;
import com.booking.exception.ValidationException;
import com.booking.model.Hotel;

import java.sql.SQLException;
import java.util.List;

public class HotelService {

    private final HotelDAO hotelDAO;

    public HotelService() {
        this.hotelDAO = new HotelDAOImpl();
    }

    public void createHotel(Hotel hotel) throws SQLException {

        if (hotel == null) {
            throw new ValidationException("Hotel cannot be null");
        }

        if (hotel.getName() == null ||
                hotel.getName().trim().isEmpty()) {
            throw new ValidationException("Hotel name is required");
        }

        if (hotel.getAddress() == null ||
                hotel.getAddress().trim().isEmpty()) {
            throw new ValidationException("Hotel address is required");
        }

        if (hotel.getStarRating() != null &&
                (hotel.getStarRating() < 0 ||
                        hotel.getStarRating() > 5)) {
            throw new ValidationException(
                    "Star rating must be between 0 and 5"
            );
        }

        if (hotel.getStatus() == null ||
                hotel.getStatus().trim().isEmpty()) {
            throw new ValidationException("Hotel status is required");
        }

        hotelDAO.save(hotel);
    }

    public Hotel getHotelById(Long hotelId) throws SQLException {
        return hotelDAO.findById(hotelId);
    }

    public List<Hotel> getAllHotels() throws SQLException {
        return hotelDAO.findAll();
    }

    public List<Hotel> getHotelsByLocationId(Long locationId)
            throws SQLException {
        return hotelDAO.findByLocationId(locationId);
    }

    public void updateHotel(Hotel hotel) throws SQLException {
        hotelDAO.update(hotel);
    }

    public void deleteHotel(Long hotelId) throws SQLException {
        hotelDAO.delete(hotelId);
    }
}