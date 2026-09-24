package com.booking.controller;

import com.booking.model.Payment;
import com.booking.util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;

public class PaymentControllerTest {

    public static void main(String[] args) {

        try {
            Long bookingId = getExistingBookingId();

            if (bookingId == null) {
                System.out.println(
                        "No confirmed booking found. Create a booking first."
                );
                return;
            }

            System.out.println("Booking ID: " + bookingId);

            PaymentController controller =
                    new PaymentController();

            Payment payment = new Payment();

            payment.setBookingId(bookingId);
            payment.setAmount(5000.00);
            payment.setPaymentStatus("SUCCESS");

            String transactionRef =
                    "TXN" + System.currentTimeMillis();

            payment.setTransactionRef(transactionRef);
            payment.setPaidAt(LocalDateTime.now());

            // Create payment
            controller.createPayment(payment);

            System.out.println(
                    "Payment created successfully!"
            );

            // Find the newly created payment
            Payment createdPayment = null;

            for (Payment p : controller.getAllPayments()) {

                if (transactionRef.equals(
                        p.getTransactionRef())) {

                    createdPayment = p;
                    break;
                }
            }

            if (createdPayment == null) {
                System.out.println(
                        "New payment was not found."
                );
                return;
            }

            System.out.println();
            System.out.println(
                    "Payment ID: "
                            + createdPayment.getPaymentId()
            );

            System.out.println(
                    "Payment Status: "
                            + createdPayment.getPaymentStatus()
            );

            // Refund the exact payment
            controller.refundPayment(
                    createdPayment.getPaymentId()
            );

            System.out.println(
                    "Payment refunded successfully!"
            );

            // Verify refund
            Payment refundedPayment =
                    controller.getPaymentById(
                            createdPayment.getPaymentId()
                    );

            System.out.println(
                    "Final Payment Status: "
                            + refundedPayment.getPaymentStatus()
            );

        } catch (Exception e) {

            System.out.println("Error occurred!");
            e.printStackTrace();
        }
    }

    private static Long getExistingBookingId()
            throws Exception {

        String sql =
                "SELECT booking_id " +
                        "FROM booking " +
                        "WHERE booking_status = 'CONFIRMED' " +
                        "ORDER BY booking_id DESC " +
                        "LIMIT 1";

        try (Connection connection =
                     DBConnection.getConnection();
             Statement statement =
                     connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery(sql)) {

            if (resultSet.next()) {
                return resultSet.getLong("booking_id");
            }
        }

        return null;
    }
}