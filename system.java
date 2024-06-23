import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}

class Reservation {
    private String reservationId;
    private String userId;
    private String reservationDetails;

    public Reservation(String reservationId, String userId, String reservationDetails) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.reservationDetails = reservationDetails;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getUserId() {
        return userId;
    }

    public String getReservationDetails() {
        return reservationDetails;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + ", User ID: " + userId + ", Details: " + reservationDetails;
    }
}

class ReservationSystem {
    private HashMap<String, User> users = new HashMap<>();
    private HashMap<String, Reservation> reservations = new HashMap<>();
    private int reservationCounter = 1;

    public boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false; // User already exists
        }
        users.put(username, new User(username, password));
        return true;
    }

    public User loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            return user;
        }
        return null; // Login failed
    }

    public String makeReservation(String userId, String details) {
        String reservationId = "R" + reservationCounter++;
        Reservation reservation = new Reservation(reservationId, userId, details);
        reservations.put(reservationId, reservation);
        return reservationId;
    }

    public ArrayList<Reservation> getUserReservations(String userId) {
        ArrayList<Reservation> userReservations = new ArrayList<>();
        for (Reservation reservation : reservations.values()) {
            if (reservation.getUserId().equals(userId)) {
                userReservations.add(reservation);
            }
        }
        return userReservations;
    }
}

public class OnlineReservationSystem {
    private static ReservationSystem reservationSystem = new ReservationSystem();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to the Online Reservation System!");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (reservationSystem.registerUser(username, password)) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Username already exists. Try another one.");
        }
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = reservationSystem.loginUser(username, password);
        if (user != null) {
            System.out.println("Login successful!");
            userMenu(user);
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

    private static void userMenu(User user) {
        while (true) {
            System.out.println("1. Make a reservation");
            System.out.println("2. View reservations");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    makeReservation(user);
                    break;
                case 2:
                    viewReservations(user);
                    break;
                case 3:
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void makeReservation(User user) {
        System.out.print("Enter reservation details: ");
        String details = scanner.nextLine();
        String reservationId = reservationSystem.makeReservation(user.getUsername(), details);
        System.out.println("Reservation made successfully! Your reservation ID is " + reservationId);
    }

    private static void viewReservations(User user) {
        ArrayList<Reservation> reservations = reservationSystem.getUserReservations(user.getUsername());
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            System.out.println("Your reservations:");
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }
}