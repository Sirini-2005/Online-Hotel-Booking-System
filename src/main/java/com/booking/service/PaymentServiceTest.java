package com.booking.service;

import com.booking.model.Payment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;

import com.booking.util.DBConnection;

public class PaymentServiceTest {

    public static void main(String[] args) {

        try {

            PaymentService paymentService = new PaymentService();

            Long bookingId = getExistingBookingId();

            if (bookingId == null) {
                System.out.println("No booking found.");
                System.out.println("Please create a booking first.");
                return;
            }

            Payment payment = new Payment();

            payment.setBookingId(bookingId);
            payment.setAmount(5000.00);
            payment.setPaymentStatus("SUCCESS");
            payment.setTransactionRef(
                    "TEST-TXN-" + System.currentTimeMillis()
            );
            payment.setPaidAt(LocalDateTime.now());

            paymentService.createPayment(payment);

            System.out.println("Payment created successfully!");
            System.out.println("Payment validation passed.");

        } catch (Exception e) {

            System.out.println("Error occurred!");
            e.printStackTrace();
        }
    }

    private static Long getExistingBookingId() throws Exception {

        String sql =
                "SELECT booking_id FROM booking " +
                        "ORDER BY booking_id LIMIT 1";

        try (
                Connection connection =
                        DBConnection.getConnection();

                Statement statement =
                        connection.createStatement();

                ResultSet resultSet =
                        statement.executeQuery(sql)
        ) {

            if (resultSet.next()) {
                return resultSet.getLong("booking_id");
            }
        }

        return null;
    }
}
