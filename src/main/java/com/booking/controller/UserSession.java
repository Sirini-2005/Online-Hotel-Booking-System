package com.booking.controller;

import com.booking.model.User;

public final class UserSession {

    private static User loggedInUser;

    private UserSession() {
    }

    public static void login(User user) {
        loggedInUser = user;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public static void logout() {
        loggedInUser = null;
    }
}
