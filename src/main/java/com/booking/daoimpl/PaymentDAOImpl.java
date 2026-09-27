package com.booking.daoimpl;

import com.booking.dao.PaymentDAO;
import com.booking.model.Payment;
import com.booking.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    private static final Logger logger =
            LoggerFactory.getLogger(PaymentDAOImpl.class);

    @Override
    public void save(Payment payment) throws SQLException {

        String sql = "INSERT INTO payment " +
                "(booking_id, amount, payment_status, transaction_ref, paid_at) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, payment.getBookingId());
            ps.setDouble(2, payment.getAmount());
            ps.setString(3, payment.getPaymentStatus());
            ps.setString(4, payment.getTransactionRef());

            if (payment.getPaidAt() != null) {
                ps.setTimestamp(
                        5,
                        Timestamp.valueOf(payment.getPaidAt())
                );
            } else {
                ps.setNull(5, Types.TIMESTAMP);
            }

            ps.executeUpdate();

            logger.info("Payment saved successfully");
        }
    }

    @Override
    public Payment findById(Long paymentId) throws SQLException {

        String sql = "SELECT * FROM payment WHERE payment_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, paymentId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                logger.info("Payment found");
                return mapPayment(rs);
            }
        }

        logger.warn("Payment not found");
        return null;
    }

    @Override
    public List<Payment> findAll() throws SQLException {

        String sql = "SELECT * FROM payment";

        List<Payment> payments = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                payments.add(mapPayment(rs));
            }
        }

        logger.info("All payments fetched");

        return payments;
    }

    @Override
    public Payment findByBookingId(Long bookingId)
            throws SQLException {

        String sql = "SELECT * FROM payment WHERE booking_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, bookingId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                logger.info("Payment found for booking");
                return mapPayment(rs);
            }
        }

        logger.warn("Payment not found for booking");
        return null;
    }

    @Override
    public void update(Payment payment) throws SQLException {

        String sql = "UPDATE payment SET " +
                "booking_id = ?, amount = ?, payment_status = ?, " +
                "transaction_ref = ?, paid_at = ? " +
                "WHERE payment_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, payment.getBookingId());
            ps.setDouble(2, payment.getAmount());
            ps.setString(3, payment.getPaymentStatus());
            ps.setString(4, payment.getTransactionRef());

            if (payment.getPaidAt() != null) {
                ps.setTimestamp(
                        5,
                        Timestamp.valueOf(payment.getPaidAt())
                );
            } else {
                ps.setNull(5, Types.TIMESTAMP);
            }

            ps.setLong(6, payment.getPaymentId());

            ps.executeUpdate();

            logger.info("Payment updated successfully");
        }
    }

    @Override
    public void delete(Long paymentId) throws SQLException {

        String sql = "DELETE FROM payment WHERE payment_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, paymentId);

            ps.executeUpdate();

            logger.info("Payment deleted successfully");
        }
    }

    private Payment mapPayment(ResultSet rs) throws SQLException {

        Payment payment = new Payment();

        payment.setPaymentId(rs.getLong("payment_id"));
        payment.setBookingId(rs.getLong("booking_id"));
        payment.setAmount(rs.getDouble("amount"));
        payment.setPaymentStatus(rs.getString("payment_status"));
        payment.setTransactionRef(rs.getString("transaction_ref"));

        Timestamp paidAt = rs.getTimestamp("paid_at");

        if (paidAt != null) {
            payment.setPaidAt(paidAt.toLocalDateTime());
        }

        return payment;
    }
}