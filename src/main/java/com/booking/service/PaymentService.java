package com.booking.service;

import com.booking.dao.BookingDAO;
import com.booking.dao.PaymentDAO;
import com.booking.daoimpl.BookingDAOImpl;
import com.booking.daoimpl.PaymentDAOImpl;
import com.booking.exception.ValidationException;
import com.booking.model.Booking;
import com.booking.model.Payment;
import com.booking.util.LoggerUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class PaymentService {

    private final PaymentDAO paymentDAO;
    private final BookingDAO bookingDAO;

    private static final Logger logger =
            LoggerUtil.getLogger(PaymentService.class);

    public PaymentService() {
        this.paymentDAO = new PaymentDAOImpl();
        this.bookingDAO = new BookingDAOImpl();
    }

    public void createPayment(Payment payment) throws SQLException {

        if (payment == null) {
            logger.warning("Payment creation failed: Payment is null");
            throw new ValidationException("Payment cannot be null");
        }

        if (payment.getBookingId() == null) {
            logger.warning("Payment creation failed: Booking ID is missing");
            throw new ValidationException("Booking ID is required");
        }

        if (payment.getAmount() == null || payment.getAmount() < 0) {
            logger.warning("Payment creation failed: Invalid payment amount");
            throw new ValidationException(
                    "Payment amount cannot be negative"
            );
        }

        if (payment.getPaymentStatus() == null ||
                payment.getPaymentStatus().trim().isEmpty()) {

            logger.warning(
                    "Payment creation failed: Payment status is missing"
            );

            throw new ValidationException(
                    "Payment status is required"
            );
        }

        String paymentStatus =
                payment.getPaymentStatus().trim().toUpperCase();

        if (!paymentStatus.equals("PENDING") &&
                !paymentStatus.equals("SUCCESS") &&
                !paymentStatus.equals("FAILED") &&
                !paymentStatus.equals("REFUNDED")) {

            logger.warning(
                    "Payment creation failed: Invalid payment status"
            );

            throw new ValidationException(
                    "Invalid payment status"
            );
        }

        payment.setPaymentStatus(paymentStatus);

        Booking booking =
                bookingDAO.findById(payment.getBookingId());

        if (booking == null) {
            logger.warning(
                    "Payment creation failed: Booking not found. Booking ID: "
                            + payment.getBookingId()
            );

            throw new ValidationException(
                    "Booking not found"
            );
        }

        if ("CANCELLED".equalsIgnoreCase(
                booking.getBookingStatus())) {

            logger.warning(
                    "Payment creation failed: Booking is cancelled. Booking ID: "
                            + payment.getBookingId()
            );

            throw new ValidationException(
                    "Payment cannot be created for a cancelled booking"
            );
        }

        paymentDAO.save(payment);

        logger.info(
                "Payment created successfully for Booking ID: "
                        + payment.getBookingId()
        );
    }

    public Payment getPaymentById(Long paymentId)
            throws SQLException {

        if (paymentId == null) {
            logger.warning(
                    "Get payment failed: Payment ID is missing"
            );

            throw new ValidationException(
                    "Payment ID is required"
            );
        }

        Payment payment = paymentDAO.findById(paymentId);

        if (payment != null) {
            logger.info(
                    "Payment retrieved successfully. Payment ID: "
                            + paymentId
            );
        } else {
            logger.warning(
                    "Payment not found. Payment ID: "
                            + paymentId
            );
        }

        return payment;
    }

    public List<Payment> getAllPayments()
            throws SQLException {

        logger.info("Fetching all payments");

        return paymentDAO.findAll();
    }

    public Payment getPaymentByBookingId(Long bookingId)
            throws SQLException {

        if (bookingId == null) {
            logger.warning(
                    "Get payment failed: Booking ID is missing"
            );

            throw new ValidationException(
                    "Booking ID is required"
            );
        }

        logger.info(
                "Fetching payment for Booking ID: "
                        + bookingId
        );

        return paymentDAO.findByBookingId(bookingId);
    }

    public void updatePayment(Payment payment)
            throws SQLException {

        if (payment == null) {
            logger.warning(
                    "Payment update failed: Payment is null"
            );

            throw new ValidationException(
                    "Payment cannot be null"
            );
        }

        if (payment.getPaymentId() == null) {
            throw new ValidationException(
                    "Payment ID is required"
            );
        }

        if (payment.getBookingId() == null) {
            throw new ValidationException(
                    "Booking ID is required"
            );
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

        String paymentStatus =
                payment.getPaymentStatus().trim().toUpperCase();

        if (!paymentStatus.equals("PENDING") &&
                !paymentStatus.equals("SUCCESS") &&
                !paymentStatus.equals("FAILED") &&
                !paymentStatus.equals("REFUNDED")) {

            throw new ValidationException(
                    "Invalid payment status"
            );
        }

        payment.setPaymentStatus(paymentStatus);

        paymentDAO.update(payment);

        logger.info(
                "Payment updated successfully. Payment ID: "
                        + payment.getPaymentId()
        );
    }

    public void refundPayment(Long paymentId)
            throws SQLException {

        if (paymentId == null) {
            logger.warning(
                    "Refund failed: Payment ID is missing"
            );

            throw new ValidationException(
                    "Payment ID is required"
            );
        }

        Payment payment =
                paymentDAO.findById(paymentId);

        if (payment == null) {
            logger.warning(
                    "Refund failed: Payment not found. Payment ID: "
                            + paymentId
            );

            throw new ValidationException(
                    "Payment not found"
            );
        }

        if ("REFUNDED".equalsIgnoreCase(
                payment.getPaymentStatus())) {

            logger.warning(
                    "Payment is already refunded. Payment ID: "
                            + paymentId
            );

            throw new ValidationException(
                    "Payment is already refunded"
            );
        }

        if (!"SUCCESS".equalsIgnoreCase(
                payment.getPaymentStatus())) {

            logger.warning(
                    "Refund failed: Payment is not successful. Payment ID: "
                            + paymentId
            );

            throw new ValidationException(
                    "Only successful payments can be refunded"
            );
        }

        payment.setPaymentStatus("REFUNDED");

        paymentDAO.update(payment);

        logger.info(
                "Payment refunded successfully. Payment ID: "
                        + paymentId
        );
    }

    public void deletePayment(Long paymentId)
            throws SQLException {

        if (paymentId == null) {
            logger.warning(
                    "Payment deletion failed: Payment ID is missing"
            );

            throw new ValidationException(
                    "Payment ID is required"
            );
        }

        paymentDAO.delete(paymentId);

        logger.info(
                "Payment deleted successfully. Payment ID: "
                        + paymentId
        );
    }
}