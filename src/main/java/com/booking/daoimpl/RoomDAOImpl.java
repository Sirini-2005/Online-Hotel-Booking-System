package com.booking.daoimpl;

import com.booking.dao.RoomDAO;
import com.booking.model.Room;
import com.booking.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {

    private static final Logger logger =
            LoggerFactory.getLogger(RoomDAOImpl.class);

    @Override
    public void save(Room room) throws SQLException {

        String sql = "INSERT INTO room " +
                "(hotel_id, room_number, room_type, capacity, base_price, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, room.getHotelId());
            ps.setString(2, room.getRoomNumber());
            ps.setString(3, room.getRoomType());
            ps.setInt(4, room.getCapacity());
            ps.setDouble(5, room.getBasePrice());
            ps.setString(6, room.getStatus());

            ps.executeUpdate();

            logger.info("Room saved successfully");
        }
    }

    @Override
    public Room findById(Long roomId) throws SQLException {

        String sql = "SELECT * FROM room WHERE room_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, roomId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                logger.info("Room found");
                return mapRoom(rs);
            }
        }

        logger.warn("Room not found");
        return null;
    }

    @Override
    public List<Room> findAll() throws SQLException {

        String sql = "SELECT * FROM room";

        List<Room> rooms = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                rooms.add(mapRoom(rs));
            }
        }

        logger.info("All rooms fetched");

        return rooms;
    }

    @Override
    public List<Room> findByHotelId(Long hotelId) throws SQLException {

        String sql = "SELECT * FROM room WHERE hotel_id = ?";

        List<Room> rooms = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, hotelId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                rooms.add(mapRoom(rs));
            }
        }

        logger.info("Rooms fetched by hotel");

        return rooms;
    }

    @Override
    public void update(Room room) throws SQLException {

        String sql = "UPDATE room SET " +
                "hotel_id = ?, room_number = ?, room_type = ?, " +
                "capacity = ?, base_price = ?, status = ? " +
                "WHERE room_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, room.getHotelId());
            ps.setString(2, room.getRoomNumber());
            ps.setString(3, room.getRoomType());
            ps.setInt(4, room.getCapacity());
            ps.setDouble(5, room.getBasePrice());
            ps.setString(6, room.getStatus());
            ps.setLong(7, room.getRoomId());

            ps.executeUpdate();

            logger.info("Room updated successfully");
        }
    }

    @Override
    public void delete(Long roomId) throws SQLException {

        String sql = "DELETE FROM room WHERE room_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, roomId);

            ps.executeUpdate();

            logger.info("Room deleted successfully");
        }
    }

    private Room mapRoom(ResultSet rs) throws SQLException {

        Room room = new Room();

        room.setRoomId(rs.getLong("room_id"));
        room.setHotelId(rs.getLong("hotel_id"));
        room.setRoomNumber(rs.getString("room_number"));
        room.setRoomType(rs.getString("room_type"));
        room.setCapacity(rs.getInt("capacity"));
        room.setBasePrice(rs.getDouble("base_price"));
        room.setStatus(rs.getString("status"));

        return room;
    }
}