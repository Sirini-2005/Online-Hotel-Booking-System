package com.booking.daoimpl;

import com.booking.dao.HotelDAO;
import com.booking.model.Hotel;
import com.booking.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDAOImpl implements HotelDAO {

    private static final Logger logger =
            LoggerFactory.getLogger(HotelDAOImpl.class);

    @Override
    public void save(Hotel hotel) throws SQLException {

        String sql = "INSERT INTO hotel " +
                "(location_id, name, description, address, star_rating, amenities, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (hotel.getLocationId() != null) {
                ps.setLong(1, hotel.getLocationId());
            } else {
                ps.setNull(1, Types.BIGINT);
            }

            ps.setString(2, hotel.getName());
            ps.setString(3, hotel.getDescription());
            ps.setString(4, hotel.getAddress());

            if (hotel.getStarRating() != null) {
                ps.setDouble(5, hotel.getStarRating());
            } else {
                ps.setNull(5, Types.DECIMAL);
            }

            ps.setString(6, hotel.getAmenities());
            ps.setString(7, hotel.getStatus());

            ps.executeUpdate();

            logger.info("Hotel saved successfully");
        }
    }

    @Override
    public Hotel findById(Long hotelId) throws SQLException {

        String sql = "SELECT * FROM hotel WHERE hotel_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, hotelId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                logger.info("Hotel found");
                return mapHotel(rs);
            }
        }

        logger.warn("Hotel not found");
        return null;
    }

    @Override
    public List<Hotel> findAll() throws SQLException {

        String sql = "SELECT * FROM hotel";

        List<Hotel> hotels = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                hotels.add(mapHotel(rs));
            }
        }

        logger.info("All hotels fetched");

        return hotels;
    }

    @Override
    public List<Hotel> findByLocationId(Long locationId)
            throws SQLException {

        String sql = "SELECT * FROM hotel WHERE location_id = ?";

        List<Hotel> hotels = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, locationId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                hotels.add(mapHotel(rs));
            }
        }

        logger.info("Hotels fetched by location");

        return hotels;
    }

    @Override
    public void update(Hotel hotel) throws SQLException {

        String sql = "UPDATE hotel SET " +
                "location_id = ?, name = ?, description = ?, " +
                "address = ?, star_rating = ?, amenities = ?, status = ? " +
                "WHERE hotel_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (hotel.getLocationId() != null) {
                ps.setLong(1, hotel.getLocationId());
            } else {
                ps.setNull(1, Types.BIGINT);
            }

            ps.setString(2, hotel.getName());
            ps.setString(3, hotel.getDescription());
            ps.setString(4, hotel.getAddress());

            if (hotel.getStarRating() != null) {
                ps.setDouble(5, hotel.getStarRating());
            } else {
                ps.setNull(5, Types.DECIMAL);
            }

            ps.setString(6, hotel.getAmenities());
            ps.setString(7, hotel.getStatus());
            ps.setLong(8, hotel.getHotelId());

            ps.executeUpdate();

            logger.info("Hotel updated successfully");
        }
    }

    @Override
    public void delete(Long hotelId) throws SQLException {

        String sql = "DELETE FROM hotel WHERE hotel_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, hotelId);

            ps.executeUpdate();

            logger.info("Hotel deleted successfully");
        }
    }

    private Hotel mapHotel(ResultSet rs) throws SQLException {

        Hotel hotel = new Hotel();

        hotel.setHotelId(rs.getLong("hotel_id"));

        long locationId = rs.getLong("location_id");

        if (rs.wasNull()) {
            hotel.setLocationId(null);
        } else {
            hotel.setLocationId(locationId);
        }

        hotel.setName(rs.getString("name"));
        hotel.setDescription(rs.getString("description"));
        hotel.setAddress(rs.getString("address"));

        double starRating = rs.getDouble("star_rating");

        if (rs.wasNull()) {
            hotel.setStarRating(null);
        } else {
            hotel.setStarRating(starRating);
        }

        hotel.setAmenities(rs.getString("amenities"));
        hotel.setStatus(rs.getString("status"));

        return hotel;
    }
}