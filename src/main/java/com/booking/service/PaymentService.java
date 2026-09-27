package com.booking.service;

import com.booking.dao.PaymentDAO;
import com.booking.daoimpl.PaymentDAOImpl;
import com.booking.exception.ValidationException;
import com.booking.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class PaymentService {

    private static final Logger logger =
            LoggerFactory.getLogger(PaymentService.class);

    private final PaymentDAO paymentDAO;

    public PaymentService() {
        this.paymentDAO = new PaymentDAOImpl();
    }

    // Create Payment
    public void createPayment(Payment payment) throws SQLException {

        logger.info("Creating payment");

        if (payment == null) {
            logger.warn("Payment is null");
            throw new ValidationException(
                    "Payment cannot be null"
            );
        }

        if (payment.getBookingId() == null) {
            logger.warn("Booking ID is missing");
            throw new ValidationException(
                    "Booking ID is required"
            );
        }

        if (payment.getAmount() == null ||
                payment.getAmount() < 0) {
            logger.warn("Invalid payment amount");
            throw new ValidationException(
                    "Amount cannot be negative"
            );
        }

        if (payment.getPaymentStatus() == null ||
                payment.getPaymentStatus().trim().isEmpty()) {
            logger.warn("Payment status is missing");
            throw new ValidationException(
                    "Payment status is required"
            );
        }

        paymentDAO.save(payment);

        logger.info("Payment created successfully");
    }

    // Get Payment By ID
    public Payment getPaymentById(Long paymentId)
            throws SQLException {

        logger.info("Getting payment by ID");

        return paymentDAO.findById(paymentId);
    }

    // Get All Payments
    public List<Payment> getAllPayments()
            throws SQLException {

        logger.info("Getting all payments");

        return paymentDAO.findAll();
    }

    // Get Payment By Booking ID
    public Payment getPaymentByBookingId(Long bookingId)
            throws SQLException {

        logger.info("Getting payment by booking ID");

        return paymentDAO.findByBookingId(bookingId);
    }

    // Update Payment
    public void updatePayment(Payment payment)
            throws SQLException {

        logger.info("Updating payment");

        paymentDAO.update(payment);

        logger.info("Payment updated successfully");
    }

    // Refund Payment
    public void refundPayment(Long paymentId)
            throws SQLException {

        logger.info("Refunding payment");

        Payment payment = paymentDAO.findById(paymentId);

        if (payment == null) {
            logger.warn("Payment not found");
            throw new ValidationException(
                    "Payment not found"
            );
        }

        payment.setPaymentStatus("REFUNDED");

        paymentDAO.update(payment);

        logger.info("Payment refunded successfully");
    }

    // Delete Payment
    public void deletePayment(Long paymentId)
            throws SQLException {

        logger.info("Deleting payment");

        paymentDAO.delete(paymentId);

        logger.info("Payment deleted successfully");
    }
}