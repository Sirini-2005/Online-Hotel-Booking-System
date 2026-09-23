package com.booking.service;

import com.booking.dao.HotelImageDAO;
import com.booking.daoimpl.HotelImageDAOImpl;
import com.booking.exception.ValidationException;
import com.booking.model.HotelImage;

import java.sql.SQLException;
import java.util.List;

public class HotelImageService {

    private final HotelImageDAO hotelImageDAO;

    public HotelImageService() {
        this.hotelImageDAO = new HotelImageDAOImpl();
    }

    public void createHotelImage(HotelImage hotelImage) throws SQLException {

        if (hotelImage == null) {
            throw new ValidationException("Hotel image cannot be null");
        }

        if (hotelImage.getHotelId() == null) {
            throw new ValidationException("Hotel ID is required");
        }

        if (hotelImage.getImageUrl() == null ||
                hotelImage.getImageUrl().trim().isEmpty()) {
            throw new ValidationException("Image URL is required");
        }

        hotelImageDAO.save(hotelImage);
    }

    public HotelImage getHotelImageById(Long imageId)
            throws SQLException {
        return hotelImageDAO.findById(imageId);
    }

    public List<HotelImage> getAllHotelImages()
            throws SQLException {
        return hotelImageDAO.findAll();
    }

    public List<HotelImage> getHotelImagesByHotelId(Long hotelId)
            throws SQLException {
        return hotelImageDAO.findByHotelId(hotelId);
    }

    public void updateHotelImage(HotelImage hotelImage)
            throws SQLException {
        hotelImageDAO.update(hotelImage);
    }

    public void deleteHotelImage(Long imageId)
            throws SQLException {
        hotelImageDAO.delete(imageId);
    }
}