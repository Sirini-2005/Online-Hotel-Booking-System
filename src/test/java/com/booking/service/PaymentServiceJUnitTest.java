package com.booking.service;

import com.booking.exception.ValidationException;
import com.booking.model.Payment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentServiceJUnitTest {

    @Test
    void shouldRejectPaymentWhenBookingIdIsMissing() {

        PaymentService paymentService = new PaymentService();

        Payment payment = new Payment();
        payment.setBookingId(null);
        payment.setAmount(5000.00);
        payment.setPaymentStatus("SUCCESS");

        assertThrows(
                ValidationException.class,
                () -> paymentService.createPayment(payment)
        );
    }

    @Test
    void shouldRejectPaymentWhenAmountIsNegative() {

        PaymentService paymentService = new PaymentService();

        Payment payment = new Payment();
        payment.setBookingId(1L);
        payment.setAmount(-500.00);
        payment.setPaymentStatus("SUCCESS");

        assertThrows(
                ValidationException.class,
                () -> paymentService.createPayment(payment)
        );
    }

    @Test
    void shouldRejectPaymentWhenStatusIsMissing() {

        PaymentService paymentService = new PaymentService();

        Payment payment = new Payment();
        payment.setBookingId(1L);
        payment.setAmount(5000.00);
        payment.setPaymentStatus("");

        assertThrows(
                ValidationException.class,
                () -> paymentService.createPayment(payment)
        );
    }
}