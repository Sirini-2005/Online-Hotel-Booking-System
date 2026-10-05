package com.booking.controller;

import com.booking.service.BookingService;
import com.booking.dao.BookingDAO;
import com.booking.daoimpl.BookingDAOImpl;
import com.booking.service.BookingServiceImpl;
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

        logger.info("Starting Hotel Booking System...");
        logger.info("Initializing application controllers...");

        userController =
                new UserController();

        logger.info("UserController initialized successfully.");

        locationController =
                new LocationController();

        logger.info("LocationController initialized successfully.");

        hotelController =
                new HotelController();

        logger.info("HotelController initialized successfully.");

        roomController =
                new RoomController();

        logger.info("RoomController initialized successfully.");

        BookingDAO bookingDAO =
                new BookingDAOImpl();

        BookingService bookingService =
                new BookingServiceImpl(bookingDAO);
        bookingController =
                new BookingController(bookingService);

        logger.info("BookingController initialized successfully.");

        paymentController =
                new PaymentController();

        logger.info("PaymentController initialized successfully.");

        reviewController =
                new ReviewController();

        logger.info("ReviewController initialized successfully.");

        hotelImageController =
                new HotelImageController();

        logger.info("HotelImageController initialized successfully.");

        logger.info("All application controllers initialized successfully.");
        logger.info("Hotel Booking System started successfully.");
    }

    public UserController getUserController() {

        logger.info("Returning UserController.");

        return userController;
    }

    public LocationController getLocationController() {

        logger.info("Returning LocationController.");

        return locationController;
    }

    public HotelController getHotelController() {

        logger.info("Returning HotelController.");

        return hotelController;
    }

    public RoomController getRoomController() {

        logger.info("Returning RoomController.");

        return roomController;
    }

    public BookingController getBookingController() {

        logger.info("Returning BookingController.");

        return bookingController;
    }

    public PaymentController getPaymentController() {

        logger.info("Returning PaymentController.");

        return paymentController;
    }

    public ReviewController getReviewController() {

        logger.info("Returning ReviewController.");

        return reviewController;
    }

    public HotelImageController getHotelImageController() {

        logger.info("Returning HotelImageController.");

        return hotelImageController;
    }
}