package com.booking.service;

import com.booking.model.Room;

public class RoomServiceTest {

    public static void main(String[] args) {

        try {

            RoomService roomService = new RoomService();

            Long hotelId = getExistingHotelId();

            if (hotelId == null) {
                System.out.println("No hotel found.");
                System.out.println("Please create a hotel first.");
                return;
            }

            Room room = new Room();

            room.setHotelId(hotelId);
            room.setRoomNumber("TEST-" + System.currentTimeMillis());
            room.setRoomType("DELUXE");
            room.setCapacity(2);
            room.setBasePrice(2500.00);
            room.setStatus("AVAILABLE");

            roomService.createRoom(room);

            System.out.println("Room created successfully!");
            System.out.println("Room validation passed.");

        } catch (Exception e) {

            System.out.println("Error occurred!");
            e.printStackTrace();
        }
    }

    private static Long getExistingHotelId() throws Exception {

        String sql =
                "SELECT hotel_id FROM hotel " +
                        "ORDER BY hotel_id LIMIT 1";

        try (
                java.sql.Connection connection =
                        com.booking.util.DBConnection.getConnection();

                java.sql.Statement statement =
                        connection.createStatement();

                java.sql.ResultSet resultSet =
                        statement.executeQuery(sql)
        ) {

            if (resultSet.next()) {
                return resultSet.getLong("hotel_id");
            }
        }

        return null;
    }
}