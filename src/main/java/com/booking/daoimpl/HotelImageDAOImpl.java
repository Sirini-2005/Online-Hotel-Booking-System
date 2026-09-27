package com.booking.daoimpl;

import com.booking.dao.HotelImageDAO;
import com.booking.model.HotelImage;
import com.booking.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelImageDAOImpl implements HotelImageDAO {

    private static final Logger logger =
            LoggerFactory.getLogger(HotelImageDAOImpl.class);

    @Override
    public void save(HotelImage image) throws SQLException {

        String sql = "INSERT INTO hotel_image " +
                "(hotel_id, image_url, caption) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, image.getHotelId());
            ps.setString(2, image.getImageUrl());
            ps.setString(3, image.getCaption());

            ps.executeUpdate();

            logger.info("Hotel image saved successfully");
        }
    }

    @Override
    public HotelImage findById(Long imageId) throws SQLException {

        String sql = "SELECT * FROM hotel_image WHERE image_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, imageId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                logger.info("Hotel image found");
                return mapImage(rs);
            }
        }

        logger.warn("Hotel image not found");
        return null;
    }

    @Override
    public List<HotelImage> findAll() throws SQLException {

        String sql = "SELECT * FROM hotel_image";

        List<HotelImage> images = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                images.add(mapImage(rs));
            }
        }

        logger.info("All hotel images fetched");

        return images;
    }

    @Override
    public List<HotelImage> findByHotelId(Long hotelId)
            throws SQLException {

        String sql = "SELECT * FROM hotel_image WHERE hotel_id = ?";

        List<HotelImage> images = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, hotelId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                images.add(mapImage(rs));
            }
        }

        logger.info("Hotel images fetched");

        return images;
    }

    @Override
    public void update(HotelImage image) throws SQLException {

        String sql = "UPDATE hotel_image SET " +
                "hotel_id = ?, image_url = ?, caption = ? " +
                "WHERE image_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, image.getHotelId());
            ps.setString(2, image.getImageUrl());
            ps.setString(3, image.getCaption());
            ps.setLong(4, image.getImageId());

            ps.executeUpdate();

            logger.info("Hotel image updated successfully");
        }
    }

    @Override
    public void delete(Long imageId) throws SQLException {

        String sql = "DELETE FROM hotel_image WHERE image_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, imageId);

            ps.executeUpdate();

            logger.info("Hotel image deleted successfully");
        }
    }

    private HotelImage mapImage(ResultSet rs) throws SQLException {

        HotelImage image = new HotelImage();

        image.setImageId(rs.getLong("image_id"));
        image.setHotelId(rs.getLong("hotel_id"));
        image.setImageUrl(rs.getString("image_url"));
        image.setCaption(rs.getString("caption"));

        return image;
    }
}