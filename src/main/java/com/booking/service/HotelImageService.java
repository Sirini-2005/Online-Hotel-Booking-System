package com.booking.service;

import com.booking.dao.HotelImageDAO;
import com.booking.daoimpl.HotelImageDAOImpl;
import com.booking.exception.ValidationException;
import com.booking.model.HotelImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class HotelImageService {

    private static final Logger logger =
            LoggerFactory.getLogger(HotelImageService.class);

    private final HotelImageDAO hotelImageDAO;

    public HotelImageService() {
        this.hotelImageDAO = new HotelImageDAOImpl();
    }

    public void createHotelImage(HotelImage hotelImage)
            throws SQLException {

        logger.info("Creating hotel image");

        if (hotelImage == null) {
            logger.warn("Hotel image is null");
            throw new ValidationException(
                    "Hotel image cannot be null"
            );
        }

        if (hotelImage.getHotelId() == null) {
            throw new ValidationException(
                    "Hotel ID is required"
            );
        }

        if (hotelImage.getImageUrl() == null ||
                hotelImage.getImageUrl().trim().isEmpty()) {
            throw new ValidationException(
                    "Image URL is required"
            );
        }

        hotelImageDAO.save(hotelImage);

        logger.info("Hotel image created successfully");
    }

    public HotelImage getHotelImageById(Long imageId)
            throws SQLException {

        logger.info("Getting hotel image by ID");

        return hotelImageDAO.findById(imageId);
    }

    public List<HotelImage> getAllHotelImages()
            throws SQLException {

        logger.info("Getting all hotel images");

        return hotelImageDAO.findAll();
    }

    public List<HotelImage> getHotelImagesByHotelId(Long hotelId)
            throws SQLException {

        logger.info("Getting hotel images by hotel");

        return hotelImageDAO.findByHotelId(hotelId);
    }

    public void updateHotelImage(HotelImage hotelImage)
            throws SQLException {

        logger.info("Updating hotel image");

        hotelImageDAO.update(hotelImage);

        logger.info("Hotel image updated successfully");
    }

    public void deleteHotelImage(Long imageId)
            throws SQLException {

        logger.info("Deleting hotel image");

        hotelImageDAO.delete(imageId);

        logger.info("Hotel image deleted successfully");
    }
}