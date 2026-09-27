package com.booking.dao;

import com.booking.model.Location;

import java.sql.SQLException;
import java.util.List;

public interface LocationDAO {

    void save(Location location) throws SQLException;

    Location findById(Long locationId) throws SQLException;

    List<Location> findAll() throws SQLException;

    List<Location> findByType(String type) throws SQLException;

    void update(Location location) throws SQLException;

    void delete(Long locationId) throws SQLException;
}