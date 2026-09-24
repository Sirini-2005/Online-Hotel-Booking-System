package com.booking.dao;

import com.booking.model.Payment;

import java.sql.SQLException;
import java.util.List;

public interface PaymentDAO {

    void save(Payment payment) throws SQLException;

    Payment findById(Long paymentId) throws SQLException;

    List<Payment> findAll() throws SQLException;

    Payment findByBookingId(Long bookingId) throws SQLException;

    void update(Payment payment) throws SQLException;

    void delete(Long paymentId) throws SQLException;
}