package com.booking.service;

import com.booking.dao.RoomDAO;
import com.booking.daoimpl.RoomDAOImpl;
import com.booking.exception.ValidationException;
import com.booking.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class RoomService {

    private static final Logger logger =
            LoggerFactory.getLogger(RoomService.class);

    private final RoomDAO roomDAO;

    public RoomService() {
        this.roomDAO = new RoomDAOImpl();
    }

    public void createRoom(Room room) throws SQLException {

        logger.info("Creating room");

        if (room == null) {
            logger.warn("Room is null");
            throw new ValidationException("Room cannot be null");
        }

        if (room.getHotelId() == null) {
            logger.warn("Hotel ID is missing");
            throw new ValidationException("Hotel ID is required");
        }

        if (room.getRoomNumber() == null ||
                room.getRoomNumber().trim().isEmpty()) {
            logger.warn("Room number is missing");
            throw new ValidationException("Room number is required");
        }

        if (room.getRoomType() == null ||
                room.getRoomType().trim().isEmpty()) {
            logger.warn("Room type is missing");
            throw new ValidationException("Room type is required");
        }

        if (room.getCapacity() == null ||
                room.getCapacity() <= 0) {
            logger.warn("Invalid room capacity");
            throw new ValidationException(
                    "Capacity must be greater than 0"
            );
        }

        if (room.getBasePrice() == null ||
                room.getBasePrice() < 0) {
            logger.warn("Invalid room price");
            throw new ValidationException(
                    "Base price cannot be negative"
            );
        }

        if (room.getStatus() == null ||
                room.getStatus().trim().isEmpty()) {
            logger.warn("Room status is missing");
            throw new ValidationException("Room status is required");
        }

        roomDAO.save(room);

        logger.info("Room created successfully");
    }

    public Room getRoomById(Long roomId)
            throws SQLException {

        logger.info("Getting room by ID");

        return roomDAO.findById(roomId);
    }

    public List<Room> getAllRooms()
            throws SQLException {

        logger.info("Getting all rooms");

        return roomDAO.findAll();
    }

    public List<Room> getRoomsByHotelId(Long hotelId)
            throws SQLException {

        logger.info("Getting rooms by hotel");

        return roomDAO.findByHotelId(hotelId);
    }

    public void updateRoom(Room room)
            throws SQLException {

        logger.info("Updating room");

        roomDAO.update(room);

        logger.info("Room updated successfully");
    }

    public void deleteRoom(Long roomId)
            throws SQLException {

        logger.info("Deleting room");

        roomDAO.delete(roomId);

        logger.info("Room deleted successfully");
    }
}