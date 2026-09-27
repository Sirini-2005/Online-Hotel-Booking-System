package com.booking.service;

import com.booking.exception.ValidationException;
import com.booking.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceJUnitTest {

    @Test
    void shouldRejectUserWhenFullNameIsMissing() {

        UserService userService = new UserService();

        User user = new User();
        user.setFullName("");
        user.setEmail("test@gmail.com");
        user.setPasswordHash("12345");
        user.setRole("CUSTOMER");
        user.setStatus("ACTIVE");

        assertThrows(
                ValidationException.class,
                () -> userService.createUser(user)
        );
    }

    @Test
    void shouldRejectUserWhenEmailIsMissing() {

        UserService userService = new UserService();

        User user = new User();
        user.setFullName("Test User");
        user.setEmail("");
        user.setPasswordHash("12345");
        user.setRole("CUSTOMER");
        user.setStatus("ACTIVE");

        assertThrows(
                ValidationException.class,
                () -> userService.createUser(user)
        );
    }

    @Test
    void shouldRejectUserWhenPasswordIsMissing() {

        UserService userService = new UserService();

        User user = new User();
        user.setFullName("Test User");
        user.setEmail("test@gmail.com");
        user.setPasswordHash("");
        user.setRole("CUSTOMER");
        user.setStatus("ACTIVE");

        assertThrows(
                ValidationException.class,
                () -> userService.createUser(user)
        );
    }
}