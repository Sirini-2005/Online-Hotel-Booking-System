package com.booking.service;

import com.booking.model.User;

public class UserServiceTest {

    public static void main(String[] args) {

        try {

            UserService userService = new UserService();

            User user = new User();

            user.setFullName("Test User");
            user.setEmail(
                    "test" + System.currentTimeMillis() + "@gmail.com"
            );
            user.setPasswordHash("12345");
            user.setPhone("9876543210");
            user.setRole("CUSTOMER");
            user.setStatus("ACTIVE");

            userService.createUser(user);

            System.out.println("User created successfully!");
            System.out.println("Password was stored using BCrypt hashing.");

        } catch (Exception e) {

            System.out.println("Error occurred!");
            e.printStackTrace();
        }
    }
}