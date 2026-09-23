package com.booking.service;

import com.booking.dao.RoomDAO;
import com.booking.daoimpl.RoomDAOImpl;
import com.booking.exception.ValidationException;
import com.booking.model.Room;

import java.sql.SQLException;
import java.util.List;

public class RoomService {

    private final RoomDAO roomDAO;

    public RoomService() {
        this.roomDAO = new RoomDAOImpl();
    }

    public void createRoom(Room room) throws SQLException {

        if (room == null) {
            throw new ValidationException("Room cannot be null");
        }

        if (room.getHotelId() == null) {
            throw new ValidationException("Hotel ID is required");
        }

        if (room.getRoomNumber() == null ||
                room.getRoomNumber().trim().isEmpty()) {
            throw new ValidationException("Room number is required");
        }

        if (room.getRoomType() == null ||
                room.getRoomType().trim().isEmpty()) {
            throw new ValidationException("Room type is required");
        }

        if (room.getCapacity() == null ||
                room.getCapacity() <= 0) {
            throw new ValidationException(
                    "Room capacity must be greater than 0"
            );
        }

        if (room.getBasePrice() == null ||
                room.getBasePrice() < 0) {
            throw new ValidationException(
                    "Room price cannot be negative"
            );
        }

        if (room.getStatus() == null ||
                room.getStatus().trim().isEmpty()) {
            throw new ValidationException("Room status is required");
        }

        roomDAO.save(room);
    }

    public Room getRoomById(Long roomId) throws SQLException {
        return roomDAO.findById(roomId);
    }

    public List<Room> getAllRooms() throws SQLException {
        return roomDAO.findAll();
    }

    public List<Room> getRoomsByHotelId(Long hotelId)
            throws SQLException {
        return roomDAO.findByHotelId(hotelId);
    }

    public void updateRoom(Room room) throws SQLException {
        roomDAO.update(room);
    }

    public void deleteRoom(Long roomId) throws SQLException {
        roomDAO.delete(roomId);
    }
}