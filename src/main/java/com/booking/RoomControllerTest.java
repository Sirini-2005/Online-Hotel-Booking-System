package com.booking;

import com.booking.controller.RoomController;
import com.booking.model.Room;

import java.util.List;

public class RoomControllerTest {

    public static void main(String[] args) {

        try {

            RoomController controller = new RoomController();

            Room room = new Room();

            room.setHotelId(1L);
            room.setRoomNumber("101");
            room.setRoomType("DELUXE");
            room.setCapacity(2);
            room.setBasePrice(3500.00);
            room.setStatus("AVAILABLE");

            controller.createRoom(room);

            System.out.println("Room created successfully!");

            List<Room> rooms = controller.getAllRooms();

            System.out.println("\nAll Rooms:");

            for (Room r : rooms) {

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