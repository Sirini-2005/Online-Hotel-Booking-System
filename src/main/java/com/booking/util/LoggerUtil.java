package com.booking.util;

import java.util.logging.Logger;

public final class LoggerUtil {

    private LoggerUtil() {
        // Prevent object creation
    }

    public static Logger getLogger(Class<?> clazz) {
        return Logger.getLogger(clazz.getName());
    }
}