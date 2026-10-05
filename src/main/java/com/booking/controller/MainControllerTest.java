
package com.booking.controller;

import com.booking.model.User;
import com.booking.model.Location;
import com.booking.model.Hotel;
import com.booking.model.Room;
import com.booking.model.Booking;
import com.booking.model.Payment;
import com.booking.model.Review;
import com.booking.model.HotelImage;
import com.booking.model.BookingDetails;
import com.booking.util.LoggerUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Logger;

public class MainControllerTest {

    private static final Logger logger =
            LoggerUtil.getLogger(MainControllerTest.class);

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        MainController mainController = new MainController();

        try {
            while (true) {

                printHeader("ONLINE HOTEL BOOKING SYSTEM");

                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.println("----------------------------------------");
                System.out.print("Choose an option: ");

                String input = sc.nextLine().trim();

                if (!input.matches("[1-3]")) {
                    System.out.println("Invalid choice. Please select 1-3.");
                    continue;
                }

                int choice = Integer.parseInt(input);

                switch (choice) {

                    case 1:
                        register(sc, mainController.getUserController());
                        break;

                    case 2:
                        if (login(sc, mainController.getUserController())) {
                            loggedInMenu(sc, mainController);
                        }
                        break;

                    case 3:
                        System.out.println();
                        System.out.println("Thank you for using Online Hotel Booking System!");
                        return;
                }
            }

        } catch (Exception e) {

            logger.severe("Application error: " + e.getMessage());
            System.out.println("Application error: " + e.getMessage());

        } finally {
            sc.close();
        }
    }

    // =========================================================
    // REGISTER
    // =========================================================

    private static void register(
            Scanner sc,
            UserController controller) {

        try {

            printHeader("REGISTER");

            User user = new User();

            System.out.print("Full Name       : ");
            user.setFullName(sc.nextLine().trim());

            System.out.print("Email           : ");
            user.setEmail(sc.nextLine().trim());

            System.out.print("Password        : ");
            String password = sc.nextLine();

            System.out.print("Phone           : ");
            user.setPhone(sc.nextLine().trim());

            /*
             * Role and status are system controlled for registration.
             */
            user.setRole("CUSTOMER");
            user.setStatus("ACTIVE");

            /*
             * UserService hashes the password during createUser().
             * Therefore do not hash it here.
             */
            user.setPasswordHash(password);

            controller.createUser(user);

            System.out.println();
            System.out.println("========================================");
            System.out.println("       REGISTRATION SUCCESSFUL");
            System.out.println("========================================");
            System.out.println("Name   : " + user.getFullName());
            System.out.println("Email  : " + user.getEmail());
            System.out.println("Role   : CUSTOMER");
            System.out.println("Status : ACTIVE");
            System.out.println("----------------------------------------");
            System.out.println("Please login to continue.");

        } catch (Exception e) {

            logger.severe("Registration failed: " + e.getMessage());
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    // =========================================================
    // LOGIN
    // =========================================================

    private static boolean login(
            Scanner sc,
            UserController controller) {

        try {

            printHeader("LOGIN");

            System.out.print("Email           : ");
            String email = sc.nextLine().trim();

            System.out.print("Password        : ");
            String password = sc.nextLine();

            User loggedUser = null;

            /*
             * Uses the existing UserController method getAllUsers()
             * so no new login method is required in the controller.
             */
            for (User user : controller.getAllUsers()) {

                if (user.getEmail() != null &&
                        user.getEmail().equalsIgnoreCase(email)) {

                    String storedHash = user.getPasswordHash();

                    if (storedHash != null &&
                            storedHash.startsWith("$2") &&
                            BCrypt.checkpw(password, storedHash)) {

                        loggedUser = user;
                    }

                    break;
                }
            }

            if (loggedUser == null) {
                System.out.println();
                System.out.println("Invalid email or password.");
                return false;
            }

            if (loggedUser.getStatus() != null &&
                    !"ACTIVE".equalsIgnoreCase(loggedUser.getStatus())) {

                System.out.println();
                System.out.println("Your account is not active.");
                return false;
            }

            UserSession.login(loggedUser);

            System.out.println();
            System.out.println("========================================");
            System.out.println("          LOGIN SUCCESSFUL");
            System.out.println("========================================");
            System.out.println("Welcome : " + loggedUser.getFullName());
            System.out.println("User ID : " + loggedUser.getUserId());
            System.out.println("Email   : " + loggedUser.getEmail());
            System.out.println("----------------------------------------");

            return true;

        } catch (Exception e) {

            logger.severe("Login failed: " + e.getMessage());
            System.out.println("Login failed: " + e.getMessage());
            return false;
        }
    }

    // =========================================================
    // LOGGED-IN MAIN MENU
    // =========================================================

    private static void loggedInMenu(
            Scanner sc,
            MainController mainController) {

        while (UserSession.isLoggedIn()) {

            User user = UserSession.getLoggedInUser();

            System.out.println();
            System.out.println("========================================");
            System.out.println("        HOTEL BOOKING SYSTEM");
            System.out.println("========================================");
            System.out.println("Logged-in User : " + user.getFullName());
            System.out.println("User ID        : " + user.getUserId());
            System.out.println("Email          : " + user.getEmail());
            System.out.println("----------------------------------------");

            System.out.println("1. User Management");
            System.out.println("2. Location Management");
            System.out.println("3. Hotel Management");
            System.out.println("4. Room Management");
            System.out.println("5. Booking Management");
            System.out.println("6. Payment Management");
            System.out.println("7. Review Management");
            System.out.println("8. Hotel Image Management");
            System.out.println("9. Logout");

            System.out.println("----------------------------------------");
            System.out.print("Choose an option: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        userMenu(sc, mainController.getUserController());
                        break;

                    case 2:
                        locationMenu(sc, mainController.getLocationController());
                        break;

                    case 3:
                        hotelMenu(sc, mainController.getHotelController());
                        break;

                    case 4:
                        roomMenu(sc, mainController.getRoomController());
                        break;

                    case 5:
                        bookingMenu(sc, mainController.getBookingController(), mainController.getHotelController(), mainController.getRoomController());
                        break;

                    case 6:
                        paymentMenu(sc, mainController.getPaymentController(), mainController);
                        break;

                    case 7:
                        reviewMenu(sc, mainController.getReviewController());
                        break;

                    case 8:
                        hotelImageMenu(sc, mainController.getHotelImageController());
                        break;

                    case 9:
                        UserSession.logout();
                        System.out.println();
                        System.out.println("Logged out successfully.");
                        break;

                    default:
                        System.out.println("Invalid choice. Please select 1-9.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }

    // =========================================================
    // USER MANAGEMENT
    // =========================================================

    private static void userMenu(
            Scanner sc,
            UserController controller) {

        while (true) {

            printLoggedInHeader("USER MANAGEMENT");

            System.out.println("1. View My Details");
            System.out.println("2. Update My Details");
            System.out.println("3. Delete My Account");
            System.out.println("4. Back");
            System.out.print("Choose an option: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:

                        User current = UserSession.getLoggedInUser();

                        System.out.println();
                        System.out.println("User ID : " + current.getUserId());
                        System.out.println("Name    : " + current.getFullName());
                        System.out.println("Email   : " + current.getEmail());
                        System.out.println("Phone   : " + current.getPhone());
                        System.out.println("Role    : " + current.getRole());
                        System.out.println("Status  : " + current.getStatus());
                        break;

                    case 2:

                        User user = UserSession.getLoggedInUser();

                        System.out.print("New Full Name: ");
                        user.setFullName(sc.nextLine().trim());

                        System.out.print("New Phone: ");
                        user.setPhone(sc.nextLine().trim());

                        controller.updateUser(user);

                        UserSession.login(user);

                        System.out.println("Your details updated successfully.");
                        break;

                    case 3:

                        System.out.println(
                                "Delete account? This cannot be undone."
                        );

                        System.out.print("Type YES to continue: ");

                        if ("YES".equalsIgnoreCase(sc.nextLine().trim())) {

                            Long userId =
                                    UserSession.getLoggedInUser().getUserId();

                            controller.deleteUser(userId);
                            UserSession.logout();

                            System.out.println("Account deleted successfully.");
                            return;
                        }

                        System.out.println("Account deletion cancelled.");
                        break;

                    case 4:
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println("User operation failed: " + e.getMessage());
            }
        }
    }

    // =========================================================
    // LOCATION MANAGEMENT
    // =========================================================

    private static void locationMenu(
            Scanner sc,
            LocationController controller) {

        while (true) {

            printLoggedInHeader("LOCATION MANAGEMENT");

            System.out.println("1. Add Location");
            System.out.println("2. View Locations");
            System.out.println("3. Update Location");
            System.out.println("4. Delete Location");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:

                        Location location = new Location();

                        System.out.print("Location Name   : ");
                        location.setName(sc.nextLine().trim());

                        System.out.print("Location Type   : ");
                        location.setType(sc.nextLine().trim());

                        System.out.print(
                                "Parent Location ID (press Enter for none): "
                        );

                        String parent = sc.nextLine().trim();

                        location.setParentId(
                                parent.isEmpty()
                                        ? null
                                        : Long.parseLong(parent)
                        );

                        controller.createLocation(location);

                        System.out.println("Location created successfully.");
                        break;

                    case 2:

                        System.out.println();
                        System.out.println("Location ID | Name | Type | Parent ID");

                        for (Location l : controller.getAllLocations()) {

                            System.out.println(
                                    l.getLocationId() + " | " +
                                            l.getName() + " | " +
                                            l.getType() + " | " +
                                            l.getParentId()
                            );
                        }
                        break;

                    case 3:

                        System.out.print("Location ID: ");
                        Long locationId =
                                Long.parseLong(sc.nextLine());

                        Location existing =
                                controller.getLocationById(locationId);

                        if (existing == null) {
                            System.out.println("Location not found.");
                            break;
                        }

                        System.out.print("New Name: ");
                        existing.setName(sc.nextLine().trim());

                        System.out.print("New Type: ");
                        existing.setType(sc.nextLine().trim());

                        System.out.print(
                                "New Parent ID (Enter for none): "
                        );

                        String newParent = sc.nextLine().trim();

                        existing.setParentId(
                                newParent.isEmpty()
                                        ? null
                                        : Long.parseLong(newParent)
                        );

                        controller.updateLocation(existing);

                        System.out.println("Location updated successfully.");
                        break;

                    case 4:

                        System.out.print("Location ID: ");
                        Long deleteId =
                                Long.parseLong(sc.nextLine());

                        controller.deleteLocation(deleteId);

                        System.out.println("Location deleted successfully.");
                        break;

                    case 5:
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println("Location operation failed: " + e.getMessage());
            }
        }
    }

    // =========================================================
    // HOTEL MANAGEMENT
    // =========================================================

    private static void hotelMenu(
            Scanner sc,
            HotelController controller) {

        while (true) {

            printLoggedInHeader("HOTEL MANAGEMENT");

            System.out.println("1. Add Hotel");
            System.out.println("2. View Hotels");
            System.out.println("3. Update Hotel");
            System.out.println("4. Delete Hotel");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:

                        Hotel hotel = new Hotel();

                        System.out.print("Location ID (Enter for none): ");
                        String loc = sc.nextLine().trim();

                        hotel.setLocationId(
                                loc.isEmpty()
                                        ? null
                                        : Long.parseLong(loc)
                        );

                        System.out.print("Hotel Name     : ");
                        hotel.setName(sc.nextLine().trim());

                        System.out.print("Description    : ");
                        hotel.setDescription(sc.nextLine().trim());

                        System.out.print("Address        : ");
                        hotel.setAddress(sc.nextLine().trim());

                        System.out.print("Star Rating    : ");
                        hotel.setStarRating(
                                Double.parseDouble(sc.nextLine())
                        );

                        System.out.print("Amenities      : ");
                        hotel.setAmenities(sc.nextLine().trim());

                        /*
                         * Status is system controlled.
                         */
                        hotel.setStatus("ACTIVE");

                        controller.createHotel(hotel);

                        System.out.println();
                        System.out.println("Hotel created successfully.");
                        System.out.println("Status : ACTIVE");
                        break;

                    case 2:

                        System.out.println();
                        System.out.println(
                                "Hotel ID | Name | Address | Rating | Status"
                        );

                        for (Hotel h : controller.getAllHotels()) {

                            System.out.println(
                                    h.getHotelId() + " | " +
                                            h.getName() + " | " +
                                            h.getAddress() + " | " +
                                            h.getStarRating() + " | " +
                                            h.getStatus()
                            );
                        }
                        break;

                    case 3:

                        System.out.print("Hotel ID: ");
                        Long hotelId =
                                Long.parseLong(sc.nextLine());

                        Hotel existing =
                                controller.getHotelById(hotelId);

                        if (existing == null) {
                            System.out.println("Hotel not found.");
                            break;
                        }

                        System.out.print("Location ID: ");
                        String newLocation = sc.nextLine().trim();

                        existing.setLocationId(
                                newLocation.isEmpty()
                                        ? null
                                        : Long.parseLong(newLocation)
                        );

                        System.out.print("Hotel Name: ");
                        existing.setName(sc.nextLine().trim());

                        System.out.print("Description: ");
                        existing.setDescription(sc.nextLine().trim());

                        System.out.print("Address: ");
                        existing.setAddress(sc.nextLine().trim());

                        System.out.print("Star Rating: ");
                        existing.setStarRating(
                                Double.parseDouble(sc.nextLine())
                        );

                        System.out.print("Amenities: ");
                        existing.setAmenities(sc.nextLine().trim());

                        controller.updateHotel(existing);

                        System.out.println("Hotel updated successfully.");
                        break;

                    case 4:

                        System.out.print("Hotel ID: ");
                        Long deleteId =
                                Long.parseLong(sc.nextLine());

                        controller.deleteHotel(deleteId);

                        System.out.println("Hotel deleted successfully.");
                        break;

                    case 5:
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println("Hotel operation failed: " + e.getMessage());
            }
        }
    }

    // =========================================================
    // ROOM MANAGEMENT
    // =========================================================

    private static void roomMenu(
            Scanner sc,
            RoomController controller) {

        while (true) {

            printLoggedInHeader("ROOM MANAGEMENT");

            System.out.println("1. Add Room");
            System.out.println("2. View Rooms");
            System.out.println("3. Update Room");
            System.out.println("4. Delete Room");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:

                        Room room = new Room();

                        System.out.print("Hotel ID       : ");
                        Long hotelId =
                                Long.parseLong(sc.nextLine());

                        System.out.print("Room Type      : ");
                        String roomType =
                                sc.nextLine().trim().toUpperCase();

                        System.out.print("Capacity       : ");
                        int capacity =
                                Integer.parseInt(sc.nextLine());

                        /*
                         * User does NOT enter:
                         * Room ID
                         * Room Number
                         * Base Price
                         * Status
                         */

                        room.setHotelId(hotelId);
                        room.setRoomType(roomType);
                        room.setCapacity(capacity);

                        String roomNumber =
                                generateRoomNumber(controller, hotelId);

                        double basePrice =
                                getBasePrice(roomType);

                        room.setRoomNumber(roomNumber);
                        room.setBasePrice(basePrice);
                        room.setStatus("AVAILABLE");

                        controller.createRoom(room);

                        System.out.println();
                        System.out.println("========================================");
                        System.out.println("          ROOM CREATED");
                        System.out.println("========================================");
                        System.out.println("Room ID     : " + room.getRoomId());
                        System.out.println("Hotel ID    : " + room.getHotelId());
                        System.out.println("Room Number : " + room.getRoomNumber());
                        System.out.println("Room Type   : " + room.getRoomType());
                        System.out.println("Capacity    : " + room.getCapacity());
                        System.out.println("Base Price  : ₹" + room.getBasePrice());
                        System.out.println("Status      : " + room.getStatus());
                        break;

                    case 2:

                        System.out.println();
                        System.out.println(
                                "Room ID | Hotel ID | Room No | Type | Capacity | Price | Status"
                        );

                        for (Room r : controller.getAllRooms()) {

                            System.out.println(
                                    r.getRoomId() + " | " +
                                            r.getHotelId() + " | " +
                                            r.getRoomNumber() + " | " +
                                            r.getRoomType() + " | " +
                                            r.getCapacity() + " | ₹" +
                                            r.getBasePrice() + " | " +
                                            r.getStatus()
                            );
                        }
                        break;

                    case 3:

                        System.out.print("Room ID: ");
                        Long roomId =
                                Long.parseLong(sc.nextLine());

                        Room existing =
                                controller.getRoomById(roomId);

                        if (existing == null) {
                            System.out.println("Room not found.");
                            break;
                        }

                        /*
                         * Room ID and Room Number are not changed by the user.
                         * Base Price is still derived from Room Type.
                         */

                        System.out.print(
                                "New Room Type [" +
                                        existing.getRoomType() +
                                        "]: "
                        );

                        String newType = sc.nextLine().trim();

                        if (!newType.isEmpty()) {
                            existing.setRoomType(
                                    newType.toUpperCase()
                            );
                            existing.setBasePrice(
                                    getBasePrice(existing.getRoomType())
                            );
                        }

                        System.out.print(
                                "New Capacity [" +
                                        existing.getCapacity() +
                                        "]: "
                        );

                        String newCapacity = sc.nextLine().trim();

                        if (!newCapacity.isEmpty()) {
                            existing.setCapacity(
                                    Integer.parseInt(newCapacity)
                            );
                        }

                        controller.updateRoom(existing);

                        System.out.println("Room updated successfully.");
                        break;

                    case 4:

                        System.out.print("Room ID: ");
                        Long deleteRoomId =
                                Long.parseLong(sc.nextLine());

                        controller.deleteRoom(deleteRoomId);

                        System.out.println("Room deleted successfully.");
                        break;

                    case 5:
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println("Room operation failed: " + e.getMessage());
            }
        }
    }

    private static String generateRoomNumber(
            RoomController controller,
            Long hotelId) {

        int max = 100;

        try {
            List<Room> rooms = controller.getAllRooms();

            for (Room r : rooms) {

                if (r.getHotelId() != null &&
                        r.getHotelId().equals(hotelId) &&
                        r.getRoomNumber() != null) {

                    try {
                        int number =
                                Integer.parseInt(r.getRoomNumber());

                        if (number > max) {
                            max = number;
                        }

                    } catch (NumberFormatException ignored) {
                        // Ignore non-numeric room numbers.
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Unable to check existing room numbers: "
                            + e.getMessage()
            );
        }

        return String.valueOf(max + 1);
    }

    private static double getBasePrice(String roomType) {

        switch (roomType.toUpperCase()) {

            case "STANDARD":
                return 1500.00;

            case "DELUXE":
                return 2500.00;

            case "SUITE":
                return 4500.00;

            case "PREMIUM":
                return 6000.00;

            default:
                return 2000.00;
        }
    }

    // =========================================================
    // BOOKING MANAGEMENT
    // =========================================================

    private static void bookingMenu(
            Scanner sc,
            BookingController controller,
            HotelController hotelController,
            RoomController roomController) {

        while (true) {

            printLoggedInHeader("BOOKING MANAGEMENT");

            System.out.println("1. Create Booking");
            System.out.println("2. View My Bookings");
            System.out.println("3. Update Booking");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Delete Booking");
            System.out.println("6. Back");
            System.out.print("Choose an option: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:

                        Booking booking = new Booking();

                        booking.setUserId(
                                UserSession.getLoggedInUser().getUserId()
                        );

                        System.out.println();
                        System.out.println("AVAILABLE HOTELS");

                        List<Hotel> hotels = hotelController.getAllHotels();

                        if (hotels == null || hotels.isEmpty()) {
                            System.out.println("No hotels available.");
                            break;
                        }

                        for (Hotel h : hotels) {
                            if ("ACTIVE".equalsIgnoreCase(h.getStatus())) {
                                System.out.println(
                                        h.getHotelId() + " | " +
                                                h.getName() + " | " +
                                                h.getAddress() + " | Rating: " +
                                                h.getStarRating()
                                );
                            }
                        }

                        System.out.print("Select Hotel ID: ");
                        Long selectedHotelId =
                                Long.parseLong(sc.nextLine());

                        Hotel selectedHotel =
                                hotelController.getHotelById(selectedHotelId);

                        if (selectedHotel == null ||
                                !"ACTIVE".equalsIgnoreCase(selectedHotel.getStatus())) {
                            System.out.println("Hotel not found or not active.");
                            break;
                        }

                        booking.setHotelId(selectedHotelId);

                        System.out.println();
                        System.out.println("AVAILABLE ROOMS");

                        List<Room> rooms = roomController.getAllRooms();
                        boolean roomFound = false;

                        for (Room r : rooms) {
                            if (r.getHotelId() != null &&
                                    r.getHotelId().equals(selectedHotelId) &&
                                    "AVAILABLE".equalsIgnoreCase(r.getStatus())) {

                                roomFound = true;

                                System.out.println(
                                        r.getRoomId() + " | Room: " +
                                                r.getRoomNumber() + " | Type: " +
                                                r.getRoomType() + " | Capacity: " +
                                                r.getCapacity() + " | ₹" +
                                                r.getBasePrice()
                                );
                            }
                        }

                        if (!roomFound) {
                            System.out.println("No available rooms for this hotel.");
                            break;
                        }

                        System.out.print("Select Room ID: ");
                        Long selectedRoomId =
                                Long.parseLong(sc.nextLine());

                        Room selectedRoom =
                                roomController.getRoomById(selectedRoomId);

                        if (selectedRoom == null ||
                                !selectedHotelId.equals(selectedRoom.getHotelId()) ||
                                !"AVAILABLE".equalsIgnoreCase(selectedRoom.getStatus())) {
                            System.out.println("Invalid or unavailable room.");
                            break;
                        }

                        booking.setRoomId(selectedRoomId);

                        DateTimeFormatter formatter =
                                DateTimeFormatter.ofPattern("dd-MM-yyyy");

                        System.out.print("Check-in Date (DD-MM-YYYY): ");
                        booking.setCheckInDate(
                                LocalDate.parse(sc.nextLine().trim(), formatter)
                        );

                        System.out.print("Check-out Date (DD-MM-YYYY): ");
                        booking.setCheckOutDate(
                                LocalDate.parse(sc.nextLine().trim(), formatter)
                        );

                        if (!booking.getCheckOutDate()
                                .isAfter(booking.getCheckInDate())) {
                            System.out.println(
                                    "Check-out date must be after check-in date."
                            );
                            break;
                        }

                        System.out.print("Guests: ");
                        int guests = Integer.parseInt(sc.nextLine());

                        if (guests <= 0 || guests > selectedRoom.getCapacity()) {
                            System.out.println(
                                    "Guests must be between 1 and " +
                                            selectedRoom.getCapacity() + "."
                            );
                            break;
                        }

                        booking.setGuests(guests);

                        long nights = ChronoUnit.DAYS.between(
                                booking.getCheckInDate(),
                                booking.getCheckOutDate()
                        );

                        double total = selectedRoom.getBasePrice() * nights;

                        booking.setTotalAmount(total);
                        booking.setBookingStatus("PENDING");

                        controller.createBooking(booking);

                        System.out.println();
                        System.out.println("========================================");
                        System.out.println("       BOOKING CREATED");
                        System.out.println("========================================");
                        System.out.println("Booking ID   : " + booking.getBookingId());
                        System.out.println("Hotel        : " + selectedHotel.getName());
                        System.out.println("Room         : " + selectedRoom.getRoomNumber());
                        System.out.println("Check-in     : " + booking.getCheckInDate());
                        System.out.println("Check-out    : " + booking.getCheckOutDate());
                        System.out.println("Nights       : " + nights);
                        System.out.println("Guests       : " + booking.getGuests());
                        System.out.println("Total Amount : ₹" + total);
                        System.out.println("Status       : PENDING");
                        break;

                    case 2:

                        String currentEmail =
                                UserSession.getLoggedInUser().getEmail();

                        System.out.println();
                        System.out.println("MY BOOKINGS");
                        System.out.println(
                                "Booking ID | Customer | Email | Hotel | Room | Type | Check-in | Check-out | Guests | Amount | Status"
                        );

                        List<BookingDetails> details =
                                controller.getAllBookingDetails();

                        boolean found = false;

                        for (BookingDetails b : details) {
                            if (b.getUserEmail() != null &&
                                    b.getUserEmail().equalsIgnoreCase(currentEmail)) {

                                found = true;

                                System.out.println(
                                        b.getBookingId() + " | " +
                                                b.getUserName() + " | " +
                                                b.getUserEmail() + " | " +
                                                b.getHotelName() + " | " +
                                                b.getRoomNumber() + " | " +
                                                b.getRoomType() + " | " +
                                                b.getCheckInDate() + " | " +
                                                b.getCheckOutDate() + " | " +
                                                b.getGuests() + " | ₹" +
                                                b.getTotalAmount() + " | " +
                                                b.getBookingStatus()
                                );
                            }
                        }

                        if (!found) {
                            System.out.println("No bookings found.");
                        }
                        break;

                    case 3:

                        System.out.print("Booking ID: ");
                        Long bookingId = Long.parseLong(sc.nextLine());

                        Booking existing = controller.getBookingById(bookingId);

                        if (existing == null) {
                            System.out.println("Booking not found.");
                            break;
                        }

                        verifyOwnership(existing.getUserId());

                        DateTimeFormatter updateFormatter =
                                DateTimeFormatter.ofPattern("dd-MM-yyyy");

                        System.out.print(
                                "New Check-in Date [" +
                                        existing.getCheckInDate() +
                                        "] (DD-MM-YYYY): "
                        );

                        String newCheckIn = sc.nextLine().trim();

                        if (!newCheckIn.isEmpty()) {
                            existing.setCheckInDate(
                                    LocalDate.parse(newCheckIn, updateFormatter)
                            );
                        }

                        System.out.print(
                                "New Check-out Date [" +
                                        existing.getCheckOutDate() +
                                        "] (DD-MM-YYYY): "
                        );

                        String newCheckOut = sc.nextLine().trim();

                        if (!newCheckOut.isEmpty()) {
                            existing.setCheckOutDate(
                                    LocalDate.parse(newCheckOut, updateFormatter)
                            );
                        }

                        if (!existing.getCheckOutDate()
                                .isAfter(existing.getCheckInDate())) {
                            System.out.println(
                                    "Check-out date must be after check-in date."
                            );
                            break;
                        }

                        System.out.print(
                                "New Guests [" + existing.getGuests() + "]: "
                        );

                        String guestsInput = sc.nextLine().trim();

                        if (!guestsInput.isEmpty()) {
                            int newGuests = Integer.parseInt(guestsInput);

                            Room room = roomController.getRoomById(existing.getRoomId());

                            if (room == null || newGuests <= 0 ||
                                    newGuests > room.getCapacity()) {
                                System.out.println("Invalid guest count.");
                                break;
                            }

                            existing.setGuests(newGuests);
                        }

                        Room updateRoom =
                                roomController.getRoomById(existing.getRoomId());

                        if (updateRoom == null) {
                            System.out.println("Room not found.");
                            break;
                        }

                        long updateNights = ChronoUnit.DAYS.between(
                                existing.getCheckInDate(),
                                existing.getCheckOutDate()
                        );

                        existing.setTotalAmount(
                                updateRoom.getBasePrice() * updateNights
                        );

                        controller.updateBooking(existing);

                        System.out.println("Booking updated successfully.");
                        break;

                    case 4:

                        System.out.print("Booking ID: ");
                        Long cancelId = Long.parseLong(sc.nextLine());

                        Booking cancelBooking =
                                controller.getBookingById(cancelId);

                        if (cancelBooking == null) {
                            System.out.println("Booking not found.");
                            break;
                        }

                        verifyOwnership(cancelBooking.getUserId());
                        controller.cancelBooking(cancelId);

                        System.out.println("Booking cancelled successfully.");
                        break;

                    case 5:

                        System.out.print("Booking ID: ");
                        Long deleteId = Long.parseLong(sc.nextLine());

                        Booking deleteBooking =
                                controller.getBookingById(deleteId);

                        if (deleteBooking == null) {
                            System.out.println("Booking not found.");
                            break;
                        }

                        verifyOwnership(deleteBooking.getUserId());
                        controller.deleteBooking(deleteId);

                        System.out.println("Booking deleted successfully.");
                        break;

                    case 6:
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println("Booking operation failed: " + e.getMessage());
            }
        }
    }

    // =========================================================
    // PAYMENT MANAGEMENT
    // =========================================================

    private static void paymentMenu(
            Scanner sc,
            PaymentController controller,
            MainController mainController) {

        while (true) {

            printLoggedInHeader("PAYMENT MANAGEMENT");

            System.out.println("1. Make Payment");
            System.out.println("2. View My Payments");
            System.out.println("3. Update Payment");
            System.out.println("4. Refund Payment");
            System.out.println("5. Delete Payment");
            System.out.println("6. Back");
            System.out.print("Choose an option: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:

                        Payment payment = new Payment();

                        System.out.print("Booking ID : ");
                        Long bookingId =
                                Long.parseLong(sc.nextLine());

                        payment.setBookingId(bookingId);

                        Booking booking =
                                findBookingForCurrentUser(
                                        mainController,
                                        bookingId
                                );

                        if (booking == null) {
                            System.out.println(
                                    "Booking not found for logged-in user."
                            );
                            break;
                        }

                        payment.setAmount(
                                booking.getTotalAmount()
                        );

                        /*
                         * Payment status is NOT entered by the user.
                         */
                        payment.setPaymentStatus("PENDING");

                        payment.setTransactionRef(
                                "TXN-" +
                                        UUID.randomUUID()
                                                .toString()
                                                .substring(0, 8)
                                                .toUpperCase()
                        );

                        /*
                         * New payment starts as PENDING.
                         * paidAt remains null until SUCCESS.
                         */
                        payment.setPaidAt(null);

                        controller.createPayment(payment);

                        System.out.println();
                        System.out.println("========================================");
                        System.out.println("         PAYMENT INITIATED");
                        System.out.println("========================================");
                        System.out.println("Payment ID      : " + payment.getPaymentId());
                        System.out.println("Booking ID      : " + payment.getBookingId());
                        System.out.println("Amount          : ₹" + payment.getAmount());
                        System.out.println("Status          : PENDING");
                        System.out.println("Transaction Ref : " + payment.getTransactionRef());
                        break;

                    case 2:

                        System.out.println();
                        System.out.println("MY PAYMENTS");

                        for (Payment p : controller.getAllPayments()) {

                            Booking b =
                                    findBookingForCurrentUser(
                                            mainController,
                                            p.getBookingId()
                                    );

                            if (b != null) {

                                System.out.println(
                                        p.getPaymentId() + " | Booking: " +
                                                p.getBookingId() + " | ₹" +
                                                p.getAmount() + " | " +
                                                p.getPaymentStatus() + " | " +
                                                p.getTransactionRef()
                                );
                            }
                        }
                        break;

                    case 3:

                        System.out.print("Payment ID: ");
                        Long paymentId =
                                Long.parseLong(sc.nextLine());

                        Payment existing =
                                controller.getPaymentById(paymentId);

                        if (existing == null) {
                            System.out.println("Payment not found.");
                            break;
                        }

                        Booking ownerBooking =
                                findBookingForCurrentUser(
                                        mainController,
                                        existing.getBookingId()
                                );

                        if (ownerBooking == null) {
                            System.out.println("You cannot modify this payment.");
                            break;
                        }

                        /*
                         * Status is system controlled.
                         * Only transaction reference can be corrected.
                         */
                        System.out.print(
                                "New Transaction Reference [" +
                                        existing.getTransactionRef() +
                                        "]: "
                        );

                        String ref = sc.nextLine().trim();

                        if (!ref.isEmpty()) {
                            existing.setTransactionRef(ref);
                        }

                        controller.updatePayment(existing);

                        System.out.println("Payment updated successfully.");
                        break;

                    case 4:

                        System.out.print("Payment ID: ");
                        Long refundId =
                                Long.parseLong(sc.nextLine());

                        Payment refund =
                                controller.getPaymentById(refundId);

                        if (refund == null) {
                            System.out.println("Payment not found.");
                            break;
                        }

                        Booking refundBooking =
                                findBookingForCurrentUser(
                                        mainController,
                                        refund.getBookingId()
                                );

                        if (refundBooking == null) {
                            System.out.println("You cannot refund this payment.");
                            break;
                        }

                        controller.refundPayment(refundId);

                        System.out.println(
                                "Refund processed. Payment status is now REFUNDED."
                        );
                        break;

                    case 5:

                        System.out.print("Payment ID: ");
                        Long deletePaymentId =
                                Long.parseLong(sc.nextLine());

                        Payment deletePayment =
                                controller.getPaymentById(deletePaymentId);

                        if (deletePayment == null) {
                            System.out.println("Payment not found.");
                            break;
                        }

                        Booking paymentBooking =
                                findBookingForCurrentUser(
                                        mainController,
                                        deletePayment.getBookingId()
                                );

                        if (paymentBooking == null) {
                            System.out.println("You cannot delete this payment.");
                            break;
                        }

                        controller.deletePayment(deletePaymentId);

                        System.out.println("Payment deleted successfully.");
                        break;

                    case 6:
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println("Payment operation failed: " + e.getMessage());
            }
        }
    }

    // =========================================================
    // REVIEW MANAGEMENT
    // =========================================================

    private static void reviewMenu(
            Scanner sc,
            ReviewController controller) {

        while (true) {

            printLoggedInHeader("REVIEW MANAGEMENT");

            System.out.println("1. Add Review");
            System.out.println("2. View My Reviews");
            System.out.println("3. Update Review");
            System.out.println("4. Delete Review");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:

                        Review review = new Review();

                        review.setUserId(
                                UserSession.getLoggedInUser().getUserId()
                        );

                        System.out.print("Hotel ID : ");
                        review.setHotelId(
                                Long.parseLong(sc.nextLine())
                        );

                        System.out.print("Rating (1-5): ");
                        review.setRating(
                                Integer.parseInt(sc.nextLine())
                        );

                        System.out.print("Comment : ");
                        review.setComment(sc.nextLine().trim());

                        controller.createReview(review);

                        System.out.println("Review added successfully.");
                        break;

                    case 2:

                        Long userId =
                                UserSession.getLoggedInUser().getUserId();

                        for (Review r : controller.getAllReviews()) {

                            if (r.getUserId() != null &&
                                    r.getUserId().equals(userId)) {

                                System.out.println(
                                        r.getReviewId() + " | Hotel: " +
                                                r.getHotelId() + " | Rating: " +
                                                r.getRating() + " | " +
                                                r.getComment()
                                );
                            }
                        }
                        break;

                    case 3:

                        System.out.print("Review ID: ");
                        Long reviewId =
                                Long.parseLong(sc.nextLine());

                        Review existing =
                                controller.getReviewById(reviewId);

                        if (existing == null) {
                            System.out.println("Review not found.");
                            break;
                        }

                        verifyOwnership(existing.getUserId());

                        System.out.print(
                                "New Rating [" +
                                        existing.getRating() +
                                        "]: "
                        );

                        String rating = sc.nextLine().trim();

                        if (!rating.isEmpty()) {
                            existing.setRating(
                                    Integer.parseInt(rating)
                            );
                        }

                        System.out.print(
                                "New Comment [" +
                                        existing.getComment() +
                                        "]: "
                        );

                        String comment = sc.nextLine();

                        if (!comment.trim().isEmpty()) {
                            existing.setComment(comment.trim());
                        }

                        controller.updateReview(existing);

                        System.out.println("Review updated successfully.");
                        break;

                    case 4:

                        System.out.print("Review ID: ");
                        Long deleteId =
                                Long.parseLong(sc.nextLine());

                        Review deleteReview =
                                controller.getReviewById(deleteId);

                        if (deleteReview == null) {
                            System.out.println("Review not found.");
                            break;
                        }

                        verifyOwnership(deleteReview.getUserId());

                        controller.deleteReview(deleteId);

                        System.out.println("Review deleted successfully.");
                        break;

                    case 5:
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println("Review operation failed: " + e.getMessage());
            }
        }
    }

    // =========================================================
    // HOTEL IMAGE MANAGEMENT
    // =========================================================

    private static void hotelImageMenu(
            Scanner sc,
            HotelImageController controller) {

        while (true) {

            printLoggedInHeader("HOTEL IMAGE MANAGEMENT");

            System.out.println("1. Add Hotel Image");
            System.out.println("2. View Hotel Images");
            System.out.println("3. Update Hotel Image");
            System.out.println("4. Delete Hotel Image");
            System.out.println("5. Back");
            System.out.print("Choose an option: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:

                        HotelImage image = new HotelImage();

                        System.out.print("Hotel ID  : ");
                        image.setHotelId(
                                Long.parseLong(sc.nextLine())
                        );

                        System.out.print("Image URL : ");
                        image.setImageUrl(sc.nextLine().trim());

                        System.out.print("Caption   : ");
                        image.setCaption(sc.nextLine().trim());

                        controller.createHotelImage(image);

                        System.out.println();
                        System.out.println("Hotel image added successfully.");
                        System.out.println(
                                "Image ID : " + image.getImageId()
                        );
                        break;

                    case 2:

                        System.out.println();
                        System.out.println(
                                "Image ID | Hotel ID | Image URL | Caption"
                        );

                        for (HotelImage i :
                                controller.getAllHotelImages()) {

                            System.out.println(
                                    i.getImageId() + " | " +
                                            i.getHotelId() + " | " +
                                            i.getImageUrl() + " | " +
                                            i.getCaption()
                            );
                        }
                        break;

                    case 3:

                        System.out.print("Image ID: ");
                        Long imageId =
                                Long.parseLong(sc.nextLine());

                        HotelImage existing =
                                controller.getHotelImageById(imageId);

                        if (existing == null) {
                            System.out.println("Hotel image not found.");
                            break;
                        }

                        System.out.print(
                                "New Hotel ID [" +
                                        existing.getHotelId() +
                                        "]: "
                        );

                        String newHotelId = sc.nextLine().trim();

                        if (!newHotelId.isEmpty()) {
                            existing.setHotelId(
                                    Long.parseLong(newHotelId)
                            );
                        }

                        System.out.print(
                                "New Image URL [" +
                                        existing.getImageUrl() +
                                        "]: "
                        );

                        String url = sc.nextLine().trim();

                        if (!url.isEmpty()) {
                            existing.setImageUrl(url);
                        }

                        System.out.print(
                                "New Caption [" +
                                        existing.getCaption() +
                                        "]: "
                        );

                        String caption = sc.nextLine();

                        if (!caption.trim().isEmpty()) {
                            existing.setCaption(caption.trim());
                        }

                        controller.updateHotelImage(existing);

                        System.out.println(
                                "Hotel image updated successfully."
                        );
                        break;

                    case 4:

                        System.out.print("Image ID: ");
                        Long deleteImageId =
                                Long.parseLong(sc.nextLine());

                        controller.deleteHotelImage(deleteImageId);

                        System.out.println(
                                "Hotel image deleted successfully."
                        );
                        break;

                    case 5:
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println(
                        "Hotel image operation failed: "
                                + e.getMessage()
                );
            }
        }
    }

    // =========================================================
    // HELPERS
    // =========================================================

    private static void verifyOwnership(Long ownerId) {

        Long loggedUserId =
                UserSession.getLoggedInUser().getUserId();

        if (ownerId == null ||
                !ownerId.equals(loggedUserId)) {

            throw new IllegalArgumentException(
                    "You can access only your own records."
            );
        }
    }

    private static Booking findBookingForCurrentUser(
            MainController controller,
            Long bookingId) {

        try {

            Booking booking =
                    controller.getBookingController()
                            .getBookingById(bookingId);

            if (booking == null) {
                return null;
            }

            Long loggedUserId =
                    UserSession.getLoggedInUser().getUserId();

            if (booking.getUserId() != null &&
                    booking.getUserId().equals(loggedUserId)) {

                return booking;
            }

            return null;

        } catch (Exception e) {
            return null;
        }
    }

    private static void printHeader(String title) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("        " + title);
        System.out.println("========================================");
    }

    private static void printLoggedInHeader(String title) {

        User user = UserSession.getLoggedInUser();

        System.out.println();
        System.out.println("========================================");
        System.out.println("        " + title);
        System.out.println("========================================");

        if (user != null) {
            System.out.println(
                    "Logged-in User : " + user.getFullName()
            );
            System.out.println(
                    "User ID        : " + user.getUserId()
            );
            System.out.println(
                    "Email          : " + user.getEmail()
            );
            System.out.println("----------------------------------------");
        }
    }
}
