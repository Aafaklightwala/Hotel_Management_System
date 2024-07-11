import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

// Room class representing individual rooms in the hotel
class Room {
    private int roomNumber;
    private String type;
    private double price;
    private boolean isOccupied;

    public Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.isOccupied = false;
    }
    public String getRoomFile() {
        return roomNumber + "" + type + "" + price + "" + ""+isOccupied;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void occupy() {
        isOccupied = true;
    }

    public void vacate() {
        isOccupied = false;
    }
}

// Hotel class representing the overall hotel management system
class HotelManagementSystem {
    private List<Room> rooms;
    private Map<String, Double> fixedPrices; // Stores fixed prices for each room type
    private Scanner scanner;

    public HotelManagementSystem() {
        rooms = new ArrayList<>();
        fixedPrices = new HashMap<>();
        fixedPrices.put("Deluxe", 150.0);
        fixedPrices.put("Supreme", 200.0);
        fixedPrices.put("Suite", 300.0);
        scanner = new Scanner(System.in);
    }

    // Method for admin to add a new room to the hotel
    public void addRoom() {
        System.out.println("Enter Room Number:");
        int roomNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Check if the room number already exists
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                System.out.println("Room " + roomNumber + " is already taken.");
                return;
            }
        }

        System.out.println("Select Room Type:");
        System.out.println("1. Deluxe");
        System.out.println("2. Supreme");
        System.out.println("3. Suite");
        int choice = scanner.nextInt();
        String type;
        double price;
        switch (choice) {
            case 1:
                type = "Deluxe";
                price = fixedPrices.get(type); // Get fixed price for Deluxe room
                break;
            case 2:
                type = "Supreme";
                price = fixedPrices.get(type); // Get fixed price for Supreme room
                break;
            case 3:
                type = "Suite";
                price = fixedPrices.get(type); // Get fixed price for Suite room
                break;
            default:
                System.out.println("Invalid choice. Setting room type to Deluxe.");
                type = "Deluxe";
                price = fixedPrices.get(type);
        }
        rooms.add(new Room(roomNumber, type, price));
        System.out.println("Room added successfully. Type: " + type + ", Price: Rs." + price);
    }

    // Method for admin to change the price of a room
    public void changeRoomPrice() {
        System.out.println("Select Room Type to Change Price:");
        System.out.println("1. Deluxe");
        System.out.println("2. Supreme");
        System.out.println("3. Suite");
        int choice = scanner.nextInt();
        double initialPrice;
        switch (choice) {
            case 1:
                initialPrice = fixedPrices.get("Deluxe");
                System.out.println("Initial price for Deluxe room: Rs." + initialPrice);
                System.out.println("Enter new price for Deluxe room:");
                double newPrice = scanner.nextDouble();
                fixedPrices.put("Deluxe", newPrice); // Update fixed price for Deluxe room
                for (Room room : rooms) {
                    if (room.getType().equals("Deluxe")) {
                        room.setPrice(newPrice);
                    }
                }
                System.out.println("Updated price for Deluxe room: Rs." + newPrice);
                break;
            case 2:
                initialPrice = fixedPrices.get("Supreme");
                System.out.println("Initial price for Supreme room: Rs." + initialPrice);
                System.out.println("Enter new price for Supreme room:");
                newPrice = scanner.nextDouble();
                fixedPrices.put("Supreme", newPrice); // Update fixed price for Supreme room
                for (Room room : rooms) {
                    if (room.getType().equals("Supreme")) {
                        room.setPrice(newPrice);
                    }
                }
                System.out.println("Updated price for Supreme room: Rs." + newPrice);
                break;
            case 3:
                initialPrice = fixedPrices.get("Suite");
                System.out.println("Initial price for Suite room: Rs." + initialPrice);
                System.out.println("Enter new price for Suite room:");
                newPrice = scanner.nextDouble();
                fixedPrices.put("Suite", newPrice); // Update fixed price for Suite room
                for (Room room : rooms) {
                    if (room.getType().equals("Suite")) {
                        room.setPrice(newPrice);
                    }
                }
                System.out.println("Updated price for Suite room: Rs." + newPrice);
                break;
            default:
                System.out.println("Invalid choice.");
        }
        System.out.println("Room prices updated successfully.");
    }

    // Method for admin to control room services (e.g., room cleaning)
    public void controlRoomService(int roomNumber, boolean clean) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                if (clean) {
                    System.out.println("Room " + roomNumber + " cleaned.");
                } else {
                    System.out.println("Room " + roomNumber + " marked for cleaning.");
                }
                return;
            }
        }
        System.out.println("Room not found.");
    }

    public void saveReservationsToFile() {
        try (FileWriter f = new FileWriter("reserved.txt")) {
            for (Room room : rooms) {
                if (room.isOccupied()) {
                    String roomDetails = room.getRoomFile();
                    f.write(roomDetails + "\n");
                }
            }
            
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Method for user to make room reservations
    public void makeReservation() {
        System.out.println("Select Room Type to reserve:");
        System.out.println("1. Deluxe");
        System.out.println("2. Supreme");
        System.out.println("3. Suite");
        int choice = scanner.nextInt();
        String type;
        switch (choice) {
            case 1:
                type = "Deluxe";
                break;
            case 2:
                type = "Supreme";
                break;
            case 3:
                type = "Suite";
                break;
            default:
                System.out.println("Invalid choice. Cancelling reservation.");
                return;
        }

        // Check if there are available rooms of the selected type
        boolean roomAvailable = false;
        for (Room room : rooms) {
            if (!room.isOccupied() && room.getType().equals(type)) {
                roomAvailable = true;
                break;
            }
        }

        if (!roomAvailable) {
            System.out.println("No available rooms of type " + type + ". Reservation failed.");
            return;
        }

        System.out.println("Enter total number of persons:");
        System.out.print("Adults: ");
        int adults = scanner.nextInt();
        System.out.print("Children: ");
        int children = scanner.nextInt();

        // Check if total persons exceed room capacity (maximum 3)
        int totalPersons = adults + children;
        if (totalPersons > 3) {
            System.out.println("Maximum 3 persons can stay in one room. Please select a different room type.");
            return;
        }

        System.out.println("Enter number of days you want to stay:");
        int days = scanner.nextInt();

        // Find an available room of the selected type
        Room availableRoom = null;
        for (Room room : rooms) {
            if (!room.isOccupied() && room.getType().equals(type)) {
                availableRoom = room;
                break;
            }
        }

        if (availableRoom != null) {
            availableRoom.occupy(); // Mark the room as occupied
            double totalPrice = availableRoom.getPrice() * days;
            System.out.println("Room " + availableRoom.getRoomNumber() + " (" + type + ") reserved successfully.");
            System.out.println("Total Price for " + days + " days stay: Rs." + totalPrice);
            
            // Save reservations to file after successful reservation
            saveReservationsToFile();
        } else {
            System.out.println("No available rooms of type " + type + ". Reservation failed.");
        }
    }

    // Method for user to view both occupied and available rooms
    public void viewRooms() {
        System.out.println("Rooms:");
        for (Room room : rooms) {
            System.out.println("Room Number: " + room.getRoomNumber() + ", Type: " + room.getType() +
                    ", Price: Rs." + room.getPrice() + ", Occupied: " + room.isOccupied());
        }
    }

    public static void main(String[] args) {
        HotelManagementSystem hotelSystem = new HotelManagementSystem();
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n-- Hotel Management System --");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Exit");
            System.out.println("Enter your choice:");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    int adminOption;
                    do {
                        System.out.println("\n-- Admin Menu --");
                        System.out.println("1. Add Room");
                        System.out.println("2. Change Room Price");
                        System.out.println("3. Control Room Service");
                        System.out.println("4. Back");
                        System.out.println("Enter your choice:");
                        adminOption = scanner.nextInt();
                        switch (adminOption) {
                            case 1:
                                hotelSystem.addRoom();
                                break;
                            case 2:
                                hotelSystem.changeRoomPrice();
                                break;
                            case 3:
                                System.out.println("Enter Room Number:");
                                int roomNumber = scanner.nextInt();
                                System.out.println("Mark room as cleaned? (true/false):");
                                boolean clean = scanner.nextBoolean();
                                hotelSystem.controlRoomService(roomNumber, clean);
                                break;
                            case 4:
                                System.out.println("Returning to main menu...");
                                break;
                            default:
                                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                        }
                    } while (adminOption != 4);
                    break;
                case 2:
                    int userOption;
                    do {
                        System.out.println("\n-- User Menu --");
                        System.out.println("1. Make Reservation");
                        System.out.println("2. View Rooms");
                        System.out.println("3. Back");
                        System.out.println("Enter your choice:");
                        userOption = scanner.nextInt();
                        switch (userOption) {
                            case 1:
                                hotelSystem.makeReservation();
                                break;
                            case 2:
                                hotelSystem.viewRooms();
                                break;
                            case 3:
                                System.out.println("Returning to main menu...");
                                break;
                            default:
                                System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                        }
                    } while (userOption != 3);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        } while (choice != 3);
        scanner.close();
    }
}