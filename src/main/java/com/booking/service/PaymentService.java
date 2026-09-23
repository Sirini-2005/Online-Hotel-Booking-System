package com.booking.service;

import com.booking.dao.PaymentDAO;
import com.booking.daoimpl.PaymentDAOImpl;
import com.booking.exception.ValidationException;
import com.booking.model.Payment;

import java.sql.SQLException;
import java.util.List;

public class PaymentService {

    private final PaymentDAO paymentDAO;

    public PaymentService() {
        this.paymentDAO = new PaymentDAOImpl();
    }

    public void createPayment(Payment payment) throws SQLException {

        if (payment == null) {
            throw new ValidationException("Payment cannot be null");
        }

        if (payment.getBookingId() == null) {
            throw new ValidationException("Booking ID is required");
        }

        if (payment.getAmount() == null ||
                payment.getAmount() < 0) {
            throw new ValidationException(
                    "Payment amount cannot be negative"
            );
        }

        if (payment.getPaymentStatus() == null ||
                payment.getPaymentStatus().trim().isEmpty()) {
            throw new ValidationException(
                    "Payment status is required"
            );
        }

        paymentDAO.save(payment);
    }

    public Payment getPaymentById(Long paymentId)
            throws SQLException {
        return paymentDAO.findById(paymentId);
    }

    public List<Payment> getAllPayments()
            throws SQLException {
        return paymentDAO.findAll();
    }

    public Payment getPaymentByBookingId(Long bookingId)
            throws SQLException {
        return paymentDAO.findByBookingId(bookingId);
    }

    public void updatePayment(Payment payment)
            throws SQLException {
        paymentDAO.update(payment);
    }

    public void deletePayment(Long paymentId)
            throws SQLException {
        paymentDAO.delete(paymentId);
    }
}