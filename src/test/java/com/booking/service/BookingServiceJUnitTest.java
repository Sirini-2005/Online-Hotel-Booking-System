package com.booking.service;

import com.booking.dao.BookingDAO;
import com.booking.exception.ValidationException;
import com.booking.model.Booking;
import org.junit.jupiter.api.Test;
import com.booking.service.BookingService;
import com.booking.service.BookingServiceImpl;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BookingServiceJUnitTest {

    @Test
    void shouldCreateBookingWhenRoomIsAvailable() throws Exception {

        BookingDAO bookingDAO = mock(BookingDAO.class);

        when(bookingDAO.isRoomAvailable(
                1L,
                LocalDate.of(2026, 10, 10),
                LocalDate.of(2026, 10, 15)
        )).thenReturn(true);

        BookingService bookingService = new BookingServiceImpl(bookingDAO);

        Booking booking = new Booking();
        booking.setUserId(1L);
        booking.setHotelId(1L);
        booking.setRoomId(1L);
        booking.setCheckInDate(LocalDate.of(2026, 10, 10));
        booking.setCheckOutDate(LocalDate.of(2026, 10, 15));
        booking.setGuests(2);
        booking.setTotalAmount(5000.00);
        booking.setBookingStatus("CONFIRMED");

        bookingService.createBooking(booking);

        verify(bookingDAO).save(booking);
    }

    @Test
    void shouldRejectBookingWhenRoomIsUnavailable() throws Exception {

        BookingDAO bookingDAO = mock(BookingDAO.class);

        when(bookingDAO.isRoomAvailable(
                1L,
                LocalDate.of(2026, 10, 10),
                LocalDate.of(2026, 10, 15)
        )).thenReturn(false);

        BookingService bookingService = new BookingServiceImpl(bookingDAO);

        Booking booking = new Booking();
        booking.setUserId(1L);
        booking.setHotelId(1L);
        booking.setRoomId(1L);
        booking.setCheckInDate(LocalDate.of(2026, 10, 10));
        booking.setCheckOutDate(LocalDate.of(2026, 10, 15));
        booking.setGuests(2);
        booking.setTotalAmount(5000.00);
        booking.setBookingStatus("CONFIRMED");

        assertThrows(
                ValidationException.class,
                () -> bookingService.createBooking(booking)
        );

        verify(bookingDAO, never()).save(booking);
    }
}