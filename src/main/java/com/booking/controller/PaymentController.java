package com.booking.controller;

import com.booking.exception.GlobalExceptionHandler;
import com.booking.model.Payment;
import com.booking.service.PaymentService;

import java.sql.SQLException;
import java.util.List;

public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController() {
        this.paymentService = new PaymentService();
    }

    public void createPayment(Payment payment) {
        try {
            paymentService.createPayment(payment);
            System.out.println("Payment created successfully!");
        } catch (Exception e) {
            GlobalExceptionHandler.handle(e);
        }
    }

    public Payment getPaymentById(Long paymentId) {

        try {
            return paymentService.getPaymentById(paymentId);
        } catch (SQLException e) {
            GlobalExceptionHandler.handle(e);
            return null;
        }
    }

    public Payment getPaymentByBookingId(Long bookingId) {

        try {
            return paymentService.getPaymentByBookingId(bookingId);
        } catch (SQLException e) {
            GlobalExceptionHandler.handle(e);
            return null;
        }
    }

    public List<Payment> getAllPayments() {

        try {
            return paymentService.getAllPayments();
        } catch (SQLException e) {
            GlobalExceptionHandler.handle(e);
            return null;
        }
    }

    public void updatePayment(Payment payment) {

        try {
            paymentService.updatePayment(payment);
            System.out.println("Payment updated successfully!");
        } catch (Exception e) {
            GlobalExceptionHandler.handle(e);
        }
    }

    public void refundPayment(Long paymentId) {

        try {
            paymentService.refundPayment(paymentId);
            System.out.println("Payment refunded successfully!");
        } catch (Exception e) {
            GlobalExceptionHandler.handle(e);
        }
    }

    public void deletePayment(Long paymentId) {

        try {
            paymentService.deletePayment(paymentId);
            System.out.println("Payment deleted successfully!");
        } catch (Exception e) {
            GlobalExceptionHandler.handle(e);
        }
    }
}