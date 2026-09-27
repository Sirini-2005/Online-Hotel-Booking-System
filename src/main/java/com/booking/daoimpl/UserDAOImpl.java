package com.booking.daoimpl;

import com.booking.dao.UserDAO;
import com.booking.model.User;
import com.booking.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final Logger logger =
            LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public void save(User user) throws SQLException {

        String sql = "INSERT INTO `user` " +
                "(full_name, email, password_hash, phone, role, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getRole());
            ps.setString(6, user.getStatus());

            ps.executeUpdate();

            logger.info("User saved successfully");
        }
    }

    @Override
    public User findById(Long userId) throws SQLException {

        String sql = "SELECT * FROM `user` WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                logger.info("User found");
                return mapUser(rs);
            }
        }

        logger.warn("User not found");
        return null;
    }

    @Override
    public User findByEmail(String email) throws SQLException {

        String sql = "SELECT * FROM `user` WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                logger.info("User found by email");
                return mapUser(rs);
            }
        }

        logger.warn("User not found by email");
        return null;
    }

    @Override
    public List<User> findAll() throws SQLException {

        String sql = "SELECT * FROM `user`";

        List<User> users = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(mapUser(rs));
            }
        }

        logger.info("All users fetched");

        return users;
    }

    @Override
    public void update(User user) throws SQLException {

        String sql = "UPDATE `user` SET " +
                "full_name = ?, email = ?, password_hash = ?, " +
                "phone = ?, role = ?, status = ? " +
                "WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getRole());
            ps.setString(6, user.getStatus());
            ps.setLong(7, user.getUserId());

            ps.executeUpdate();

            logger.info("User updated successfully");
        }
    }

    @Override
    public void delete(Long userId) throws SQLException {

        String sql = "DELETE FROM `user` WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, userId);

            ps.executeUpdate();

            logger.info("User deleted successfully");
        }
    }

    private User mapUser(ResultSet rs) throws SQLException {

        User user = new User();

        user.setUserId(rs.getLong("user_id"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setPhone(rs.getString("phone"));
        user.setRole(rs.getString("role"));
        user.setStatus(rs.getString("status"));

        return user;
    }
}