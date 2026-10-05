package com.booking.service;

import com.booking.model.Booking;
import com.booking.model.BookingDetails;


import java.util.List;

public interface BookingService {

    void createBooking(Booking booking);

    Booking getBookingById(Long bookingId);

    List<Booking> getAllBookings();

    List<Booking> getBookingsByUserId(Long userId);

    List<Booking> getBookingsByHotelId(Long hotelId);

    List<BookingDetails> getAllBookingDetails();

    void updateBooking(Booking booking);

    void cancelBooking(Long bookingId);

    void deleteBooking(Long bookingId);
}