package com.booking.service;

import com.booking.exception.ValidationException;
import com.booking.model.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LocationServiceJUnitTest {

    @Test
    void shouldRejectLocationWhenNameIsMissing() {

        LocationService locationService = new LocationService();

        Location location = new Location();
        location.setName("");
        location.setType("CITY");

        assertThrows(
                ValidationException.class,
                () -> locationService.createLocation(location)
        );
    }

    @Test
    void shouldRejectLocationWhenTypeIsMissing() {

        LocationService locationService = new LocationService();

        Location location = new Location();
        location.setName("Hyderabad");
        location.setType("");

        assertThrows(
                ValidationException.class,
                () -> locationService.createLocation(location)
        );
    }

    @Test
    void shouldRejectLocationWhenLocationIsNull() {

        LocationService locationService = new LocationService();

        assertThrows(
                ValidationException.class,
                () -> locationService.createLocation(null)
        );
    }
}