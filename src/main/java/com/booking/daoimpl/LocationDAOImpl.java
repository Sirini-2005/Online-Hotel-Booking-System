package com.booking.daoimpl;

import com.booking.dao.LocationDAO;
import com.booking.model.Location;
import com.booking.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDAOImpl implements LocationDAO {

    private static final Logger logger =
            LoggerFactory.getLogger(LocationDAOImpl.class);

    @Override
    public void save(Location location) throws SQLException {

        String sql = "INSERT INTO location (name, type, parent_id) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, location.getName());
            ps.setString(2, location.getType());

            if (location.getParentId() != null) {
                ps.setLong(3, location.getParentId());
            } else {
                ps.setNull(3, Types.BIGINT);
            }

            ps.executeUpdate();

            logger.info("Location saved successfully");
        }
    }

    @Override
    public Location findById(Long locationId) throws SQLException {

        String sql = "SELECT * FROM location WHERE location_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, locationId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                logger.info("Location found");
                return mapLocation(rs);
            }
        }

        logger.warn("Location not found");
        return null;
    }

    @Override
    public List<Location> findAll() throws SQLException {

        String sql = "SELECT * FROM location";

        List<Location> locations = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                locations.add(mapLocation(rs));
            }
        }

        logger.info("All locations fetched");

        return locations;
    }

    @Override
    public List<Location> findByType(String type) throws SQLException {

        String sql = "SELECT * FROM location WHERE type = ?";

        List<Location> locations = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, type);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                locations.add(mapLocation(rs));
            }
        }

        logger.info("Locations fetched by type");

        return locations;
    }

    @Override
    public void update(Location location) throws SQLException {

        String sql = "UPDATE location SET name = ?, type = ?, parent_id = ? " +
                "WHERE location_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, location.getName());
            ps.setString(2, location.getType());

            if (location.getParentId() != null) {
                ps.setLong(3, location.getParentId());
            } else {
                ps.setNull(3, Types.BIGINT);
            }

            ps.setLong(4, location.getLocationId());

            ps.executeUpdate();

            logger.info("Location updated successfully");
        }
    }

    @Override
    public void delete(Long locationId) throws SQLException {

        String sql = "DELETE FROM location WHERE location_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, locationId);

            ps.executeUpdate();

            logger.info("Location deleted successfully");
        }
    }

    private Location mapLocation(ResultSet rs) throws SQLException {

        Location location = new Location();

        location.setLocationId(rs.getLong("location_id"));
        location.setName(rs.getString("name"));
        location.setType(rs.getString("type"));

        long parentId = rs.getLong("parent_id");

        if (rs.wasNull()) {
            location.setParentId(null);
        } else {
            location.setParentId(parentId);
        }

        return location;
    }
}