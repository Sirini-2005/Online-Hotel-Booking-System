package com.booking.dao;

import com.booking.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

    void save(User user) throws SQLException;

    User findById(Long userId) throws SQLException;

    User findByEmail(String email) throws SQLException;

    List<User> findAll() throws SQLException;

    void update(User user) throws SQLException;

    void delete(Long userId) throws SQLException;
}