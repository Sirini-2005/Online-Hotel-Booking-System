package com.booking.controller;

import com.booking.model.Room;

public class RoomControllerTest {

    public static void main(String[] args) {

        try {
            RoomController controller = new RoomController();

            Room room = new Room();

            room.setHotelId(1L);
            room.setRoomNumber("101");
            room.setRoomType("DELUXE");
            room.setCapacity(2);
            room.setBasePrice(2500.00);
            room.setStatus("AVAILABLE");

            controller.createRoom(room);

            System.out.println("Room created successfully!");

            System.out.println("\nAll Rooms:");

            for (Room r : controller.getAllRooms()) {
                System.out.println(
                        r.getRoomId() + " | " +
                                r.getHotelId() + " | " +
                                r.getRoomNumber() + " | " +
                                r.getRoomType() + " | " +
                                r.getCapacity() + " | " +
                                r.getBasePrice() + " | " +
                                r.getStatus()
                );
            }

        } catch (Exception e) {
            System.out.println("Error occurred!");
            e.printStackTrace();
        }
    }
}