package com.booking.controller;

import com.booking.model.Hotel;
import com.booking.service.HotelService;

import java.sql.SQLException;
import java.util.List;

public class HotelController {

    private final HotelService hotelService;

    public HotelController() {
        this.hotelService = new HotelService();
    }

    public void createHotel(Hotel hotel) throws SQLException {
        hotelService.createHotel(hotel);
    }

    public Hotel getHotelById(Long hotelId) throws SQLException {
        return hotelService.getHotelById(hotelId);
    }

    public List<Hotel> getAllHotels() throws SQLException {
        return hotelService.getAllHotels();
    }

    public List<Hotel> getHotelsByLocationId(Long locationId) throws SQLException {
        return hotelService.getHotelsByLocationId(locationId);
    }

    public void updateHotel(Hotel hotel) throws SQLException {
        hotelService.updateHotel(hotel);
    }

    public void deleteHotel(Long hotelId) throws SQLException {
        hotelService.deleteHotel(hotelId);
    }
}