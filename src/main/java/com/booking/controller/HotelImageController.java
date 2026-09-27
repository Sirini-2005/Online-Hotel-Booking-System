package com.booking.controller;

import com.booking.model.HotelImage;
import com.booking.service.HotelImageService;

import java.sql.SQLException;
import java.util.List;

public class HotelImageController {

    private final HotelImageService hotelImageService;

    public HotelImageController() {
        this.hotelImageService = new HotelImageService();
    }

    public void createHotelImage(HotelImage hotelImage) throws SQLException {
        hotelImageService.createHotelImage(hotelImage);
    }

    public HotelImage getHotelImageById(Long imageId) throws SQLException {
        return hotelImageService.getHotelImageById(imageId);
    }

    public List<HotelImage> getAllHotelImages() throws SQLException {
        return hotelImageService.getAllHotelImages();
    }

    public List<HotelImage> getHotelImagesByHotelId(Long hotelId) throws SQLException {
        return hotelImageService.getHotelImagesByHotelId(hotelId);
    }

    public void updateHotelImage(HotelImage hotelImage) throws SQLException {
        hotelImageService.updateHotelImage(hotelImage);
    }

    public void deleteHotelImage(Long imageId) throws SQLException {
        hotelImageService.deleteHotelImage(imageId);
    }
}