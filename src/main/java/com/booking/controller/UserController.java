package com.booking.controller;

import com.booking.model.User;
import com.booking.service.UserService;

import java.sql.SQLException;
import java.util.List;

public class UserController {

    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public void createUser(User user) throws SQLException {
        userService.createUser(user);
    }

    public User getUserById(Long userId) throws SQLException {
        return userService.getUserById(userId);
    }

    public User getUserByEmail(String email) throws SQLException {
        return userService.getUserByEmail(email);
    }

    public List<User> getAllUsers() throws SQLException {
        return userService.getAllUsers();
    }

    public void updateUser(User user) throws SQLException {
        userService.updateUser(user);
    }

    public void deleteUser(Long userId) throws SQLException {
        userService.deleteUser(userId);
    }
}