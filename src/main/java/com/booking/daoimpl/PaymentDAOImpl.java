package com.booking.daoimpl;

import com.booking.dao.PaymentDAO;
import com.booking.model.Payment;
import com.booking.util.DBConnection;
import com.booking.util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PaymentDAOImpl implements PaymentDAO {

    private static final Logger logger =
            LoggerUtil.getLogger(PaymentDAOImpl.class);

    @Override
    public void save(Payment payment) throws SQLException {

        String sql = "INSERT INTO payment " +
                "(booking_id, amount, payment_status, transaction_ref, paid_at) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, payment.getBookingId());
            statement.setDouble(2, payment.getAmount());
            statement.setString(3, payment.getPaymentStatus());
            statement.setString(4, payment.getTransactionRef());

            if (payment.getPaidAt() != null) {
                statement.setTimestamp(
                        5,
                        Timestamp.valueOf(payment.getPaidAt())
                );
            } else {
                statement.setNull(5, Types.TIMESTAMP);
            }

            statement.executeUpdate();

            logger.info(
                    "Payment saved successfully. Booking ID: "
                            + payment.getBookingId()
            );
        }
    }

    @Override
    public Payment findById(Long paymentId) throws SQLException {

        String sql =
                "SELECT * FROM payment WHERE payment_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, paymentId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    logger.info(
                            "Payment found. Payment ID: "
                                    + paymentId
                    );

                    return mapPayment(resultSet);
                }
            }
        }

        logger.warning(
                "Payment not found. Payment ID: "
                        + paymentId
        );

        return null;
    }

    @Override
    public List<Payment> findAll() throws SQLException {

        List<Payment> payments = new ArrayList<>();

        String sql = "SELECT * FROM payment";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {
                payments.add(mapPayment(resultSet));
            }
        }

        logger.info(
                "All payments retrieved. Count: "
                        + payments.size()
        );

        return payments;
    }

    @Override
    public Payment findByBookingId(Long bookingId)
            throws SQLException {

        String sql =
                "SELECT * FROM payment WHERE booking_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, bookingId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    logger.info(
                            "Payment found for Booking ID: "
                                    + bookingId
                    );

                    return mapPayment(resultSet);
                }
            }
        }

        logger.warning(
                "No payment found for Booking ID: "
                        + bookingId
        );

        return null;
    }

    @Override
    public void update(Payment payment) throws SQLException {

        String sql = "UPDATE payment SET " +
                "booking_id = ?, " +
                "amount = ?, " +
                "payment_status = ?, " +
                "transaction_ref = ?, " +
                "paid_at = ? " +
                "WHERE payment_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, payment.getBookingId());
            statement.setDouble(2, payment.getAmount());
            statement.setString(3, payment.getPaymentStatus());
            statement.setString(4, payment.getTransactionRef());

            if (payment.getPaidAt() != null) {
                statement.setTimestamp(
                        5,
                        Timestamp.valueOf(payment.getPaidAt())
                );
            } else {
                statement.setNull(5, Types.TIMESTAMP);
            }

            statement.setLong(6, payment.getPaymentId());

            statement.executeUpdate();

            logger.info(
                    "Payment updated successfully. Payment ID: "
                            + payment.getPaymentId()
            );
        }
    }

    @Override
    public void delete(Long paymentId) throws SQLException {

        String sql =
                "DELETE FROM payment WHERE payment_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, paymentId);

            statement.executeUpdate();

            logger.info(
                    "Payment deleted successfully. Payment ID: "
                            + paymentId
            );
        }
    }

    private Payment mapPayment(ResultSet resultSet)
            throws SQLException {

        Payment payment = new Payment();

        payment.setPaymentId(
                resultSet.getLong("payment_id")
        );

        payment.setBookingId(
                resultSet.getLong("booking_id")
        );

        payment.setAmount(
                resultSet.getDouble("amount")
        );

        payment.setPaymentStatus(
                resultSet.getString("payment_status")
        );

        payment.setTransactionRef(
                resultSet.getString("transaction_ref")
        );

        Timestamp paidAt =
                resultSet.getTimestamp("paid_at");

        if (paidAt != null) {
            payment.setPaidAt(
                    paidAt.toLocalDateTime()
            );
        } else {
            payment.setPaidAt(null);
        }

        return payment;
    }
}