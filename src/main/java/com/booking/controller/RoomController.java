package com.booking.controller;

import com.booking.model.Room;
import com.booking.service.RoomService;

import java.sql.SQLException;
import java.util.List;

public class RoomController {

    private final RoomService roomService;

    public RoomController() {
        this.roomService = new RoomService();
    }

    public void createRoom(Room room) throws SQLException {
        roomService.createRoom(room);
    }

    public Room getRoomById(Long roomId) throws SQLException {
        return roomService.getRoomById(roomId);
    }

    public List<Room> getAllRooms() throws SQLException {
        return roomService.getAllRooms();
    }

    public List<Room> getRoomsByHotelId(Long hotelId) throws SQLException {
        return roomService.getRoomsByHotelId(hotelId);
    }

    public void updateRoom(Room room) throws SQLException {
        roomService.updateRoom(room);
    }

    public void deleteRoom(Long roomId) throws SQLException {
        roomService.deleteRoom(roomId);
    }
}