package com.booking.service;

import com.booking.dao.UserDAO;
import com.booking.daoimpl.UserDAOImpl;
import com.booking.exception.ValidationException;
import com.booking.model.User;
import com.booking.util.PasswordUtil;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAOImpl();
    }

    public void createUser(User user) throws SQLException {

        if (user == null) {
            throw new ValidationException("User cannot be null");
        }

        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            throw new ValidationException("Full name is required");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new ValidationException("Email is required");
        }

        if (user.getPasswordHash() == null || user.getPasswordHash().trim().isEmpty()) {
            throw new ValidationException("Password is required");
        }

        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            throw new ValidationException("Role is required");
        }

        if (user.getStatus() == null || user.getStatus().trim().isEmpty()) {
            throw new ValidationException("Status is required");
        }

        String hashedPassword =
                PasswordUtil.hashPassword(user.getPasswordHash());

        user.setPasswordHash(hashedPassword);

        userDAO.save(user);
    }

    public User getUserById(Long userId) throws SQLException {
        return userDAO.findById(userId);
    }

    public User getUserByEmail(String email) throws SQLException {
        return userDAO.findByEmail(email);
    }

    public List<User> getAllUsers() throws SQLException {
        return userDAO.findAll();
    }

    public void updateUser(User user) throws SQLException {
        userDAO.update(user);
    }

    public void deleteUser(Long userId) throws SQLException {
        userDAO.delete(userId);
    }
}