package com.booking.controller;

import com.booking.model.Payment;
import com.booking.service.PaymentService;

import java.sql.SQLException;
import java.util.List;

public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController() {
        this.paymentService = new PaymentService();
    }

    // Create payment
    public void createPayment(Payment payment)
            throws SQLException {

        paymentService.createPayment(payment);
    }

    // Get payment by ID
    public Payment getPaymentById(Long paymentId)
            throws SQLException {

        return paymentService.getPaymentById(paymentId);
    }

    // Get all payments
    public List<Payment> getAllPayments()
            throws SQLException {

        return paymentService.getAllPayments();
    }

    // Get payment by booking ID
    public Payment getPaymentByBookingId(Long bookingId)
            throws SQLException {

        return paymentService.getPaymentByBookingId(bookingId);
    }

    // Update payment
    public void updatePayment(Payment payment)
            throws SQLException {

        paymentService.updatePayment(payment);
    }

    // Refund payment
    public void refundPayment(Long paymentId)
            throws SQLException {

        paymentService.refundPayment(paymentId);
    }

    // Delete payment
    public void deletePayment(Long paymentId)
            throws SQLException {

        paymentService.deletePayment(paymentId);
    }
}