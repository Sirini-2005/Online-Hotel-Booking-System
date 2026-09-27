package com.booking.controller;

import com.booking.util.LoggerUtil;

import java.util.logging.Logger;

public class MainController {

    private static final Logger logger =
            LoggerUtil.getLogger(MainController.class);

    private final UserController userController;
    private final LocationController locationController;
    private final HotelController hotelController;
    private final RoomController roomController;
    private final BookingController bookingController;
    private final PaymentController paymentController;
    private final ReviewController reviewController;
    private final HotelImageController hotelImageController;

    public MainController() {

        logger.info("Initializing application controllers...");

        userController = new UserController();
        locationController = new LocationController();
        hotelController = new HotelController();
        roomController = new RoomController();
        bookingController = new BookingController();
        paymentController = new PaymentController();
        reviewController = new ReviewController();
        hotelImageController = new HotelImageController();

        logger.info("All controllers initialized successfully.");
    }

    public UserController getUserController() {
        return userController;
    }

    public LocationController getLocationController() {
        return locationController;
    }

    public HotelController getHotelController() {
        return hotelController;
    }

    public RoomController getRoomController() {
        return roomController;
    }

    public BookingController getBookingController() {
        return bookingController;
    }

    public PaymentController getPaymentController() {
        return paymentController;
    }

    public ReviewController getReviewController() {
        return reviewController;
    }

    public HotelImageController getHotelImageController() {
        return hotelImageController;
    }
}