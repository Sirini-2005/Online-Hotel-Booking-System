package com.booking.service;

import com.booking.exception.ValidationException;
import com.booking.model.HotelImage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class HotelImageServiceJUnitTest {

    @Test
    void shouldRejectImageWhenHotelIdIsMissing() {

        HotelImageService service = new HotelImageService();

        HotelImage image = new HotelImage();
        image.setHotelId(null);
        image.setImageUrl("https://example.com/hotel.jpg");

        assertThrows(
                ValidationException.class,
                () -> service.createHotelImage(image)
        );
    }

    @Test
    void shouldRejectImageWhenImageUrlIsMissing() {

        HotelImageService service = new HotelImageService();

        HotelImage image = new HotelImage();
        image.setHotelId(1L);
        image.setImageUrl("");

        assertThrows(
                ValidationException.class,
                () -> service.createHotelImage(image)
        );
    }

    @Test
    void shouldRejectImageWhenImageIsNull() {

        HotelImageService service = new HotelImageService();

        assertThrows(
                ValidationException.class,
                () -> service.createHotelImage(null)
        );
    }
}