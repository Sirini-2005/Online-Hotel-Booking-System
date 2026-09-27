package com.booking.service;

import com.booking.exception.ValidationException;
import com.booking.model.Hotel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class HotelServiceJUnitTest {

    @Test
    void shouldRejectHotelWhenNameIsMissing() {

        HotelService hotelService = new HotelService();

        Hotel hotel = new Hotel();
        hotel.setName("");
        hotel.setAddress("Hyderabad");
        hotel.setStarRating(4.0);
        hotel.setStatus("ACTIVE");

        assertThrows(
                ValidationException.class,
                () -> hotelService.createHotel(hotel)
        );
    }

    @Test
    void shouldRejectHotelWhenAddressIsMissing() {

        HotelService hotelService = new HotelService();

        Hotel hotel = new Hotel();
        hotel.setName("Grand Hotel");
        hotel.setAddress("");
        hotel.setStarRating(4.0);
        hotel.setStatus("ACTIVE");

        assertThrows(
                ValidationException.class,
                () -> hotelService.createHotel(hotel)
        );
    }

    @Test
    void shouldRejectHotelWhenStarRatingIsInvalid() {

        HotelService hotelService = new HotelService();

        Hotel hotel = new Hotel();
        hotel.setName("Grand Hotel");
        hotel.setAddress("Hyderabad");
        hotel.setStarRating(6.0);
        hotel.setStatus("ACTIVE");

        assertThrows(
                ValidationException.class,
                () -> hotelService.createHotel(hotel)
        );
    }
}