package com.booking.controller;

import com.booking.model.User;

public class UserControllerTest {

    public static void main(String[] args) {

        try {

            UserController controller = new UserController();

            User user = new User();

            user.setFullName("Test User");

            // Generate a unique email every time
            user.setEmail(
                    "testuser" +
                            System.currentTimeMillis() +
                            "@gmail.com"
            );

            user.setPasswordHash("12345");
            user.setPhone("9876543210");
            user.setRole("CUSTOMER");
            user.setStatus("ACTIVE");

            // Create user
            controller.createUser(user);

            System.out.println(
                    "User created successfully!"
            );

            // Display all users
            System.out.println("\nAll Users:");

            for (User u : controller.getAllUsers()) {

                System.out.println(
                        u.getUserId() + " | " +
                                u.getFullName() + " | " +
                                u.getEmail() + " | " +
                                u.getRole() + " | " +
                                u.getStatus()
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error occurred!"
            );

            e.printStackTrace();
        }
    }
}