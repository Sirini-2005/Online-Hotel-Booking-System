package com.booking.dao;

import com.booking.model.Room;

import java.sql.SQLException;
import java.util.List;

public interface RoomDAO {

    void save(Room room) throws SQLException;

    Room findById(Long roomId) throws SQLException;

    List<Room> findAll() throws SQLException;

    List<Room> findByHotelId(Long hotelId) throws SQLException;

    void update(Room room) throws SQLException;

    void delete(Long roomId) throws SQLException;
}