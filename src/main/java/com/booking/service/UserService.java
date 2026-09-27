package com.booking.service;

import com.booking.dao.UserDAO;
import com.booking.daoimpl.UserDAOImpl;
import com.booking.exception.ValidationException;
import com.booking.model.User;
import com.booking.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAOImpl();
    }

    public void createUser(User user) throws SQLException {

        logger.info("Creating user");

        if (user == null) {
            logger.warn("User is null");
            throw new ValidationException("User cannot be null");
        }

        if (user.getFullName() == null ||
                user.getFullName().trim().isEmpty()) {

            logger.warn("Full name is missing");
            throw new ValidationException("Full name is required");
        }

        if (user.getEmail() == null ||
                user.getEmail().trim().isEmpty()) {

            logger.warn("Email is missing");
            throw new ValidationException("Email is required");
        }

        if (user.getPasswordHash() == null ||
                user.getPasswordHash().trim().isEmpty()) {

            logger.warn("Password is missing");
            throw new ValidationException("Password is required");
        }

        if (user.getRole() == null ||
                user.getRole().trim().isEmpty()) {

            logger.warn("Role is missing");
            throw new ValidationException("Role is required");
        }

        if (user.getStatus() == null ||
                user.getStatus().trim().isEmpty()) {

            logger.warn("Status is missing");
            throw new ValidationException("Status is required");
        }

        String hashedPassword =
                PasswordUtil.hashPassword(user.getPasswordHash());

        user.setPasswordHash(hashedPassword);

        userDAO.save(user);

        logger.info("User created successfully");
    }

    public User getUserById(Long userId) throws SQLException {

        logger.info("Getting user by ID");

        return userDAO.findById(userId);
    }

    public User getUserByEmail(String email) throws SQLException {

        logger.info("Getting user by email");

        return userDAO.findByEmail(email);
    }

    public List<User> getAllUsers() throws SQLException {

        logger.info("Getting all users");

        return userDAO.findAll();
    }

    public void updateUser(User user) throws SQLException {

        logger.info("Updating user");

        userDAO.update(user);

        logger.info("User updated successfully");
    }

    public void deleteUser(Long userId) throws SQLException {

        logger.info("Deleting user");

        userDAO.delete(userId);

        logger.info("User deleted successfully");
    }
}