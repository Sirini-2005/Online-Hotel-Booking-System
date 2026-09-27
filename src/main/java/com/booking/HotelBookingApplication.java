package com.booking;

import com.booking.controller.MainController;
import com.booking.util.LoggerUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class HotelBookingApplication {

    private static final Logger logger =
            LoggerUtil.getLogger(HotelBookingApplication.class);

    public static void main(String[] args) {

        logger.info("Starting Online Hotel Booking System...");

        SpringApplication.run(HotelBookingApplication.class, args);

        MainController mainController =
                new MainController();

        logger.info("Online Hotel Booking System Started Successfully!");
        logger.info("All controllers initialized successfully.");
    }
}