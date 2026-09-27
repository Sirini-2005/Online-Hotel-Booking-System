package com.booking.service;

import com.booking.exception.ValidationException;
import com.booking.model.Room;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RoomServiceJUnitTest {

    @Test
    void shouldRejectRoomWhenHotelIdIsMissing() {

        RoomService roomService = new RoomService();

        Room room = new Room();
        room.setHotelId(null);
        room.setRoomNumber("101");
        room.setRoomType("DELUXE");
        room.setCapacity(2);
        room.setBasePrice(2500.00);
        room.setStatus("AVAILABLE");

        assertThrows(
                ValidationException.class,
                () -> roomService.createRoom(room)
        );
    }

    @Test
    void shouldRejectRoomWhenCapacityIsInvalid() {

        RoomService roomService = new RoomService();

        Room room = new Room();
        room.setHotelId(1L);
        room.setRoomNumber("101");
        room.setRoomType("DELUXE");
        room.setCapacity(0);
        room.setBasePrice(2500.00);
        room.setStatus("AVAILABLE");

        assertThrows(
                ValidationException.class,
                () -> roomService.createRoom(room)
        );
    }

    @Test
    void shouldRejectRoomWhenBasePriceIsInvalid() {

        RoomService roomService = new RoomService();

        Room room = new Room();
        room.setHotelId(1L);
        room.setRoomNumber("101");
        room.setRoomType("DELUXE");
        room.setCapacity(2);
        room.setBasePrice(-100.00);
        room.setStatus("AVAILABLE");

        assertThrows(
                ValidationException.class,
                () -> roomService.createRoom(room)
        );
    }
}