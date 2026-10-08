import java.time.LocalDateTime;
import java.util.Scanner;
import javax.swing.SwingUtilities;

public class Main {
    private static ParkingGUI guiInstance;

    public static void main(String[] args) {
        ParkingManager manager = new ParkingManager();

        SwingUtilities.invokeLater(() -> {
            try {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            guiInstance = new ParkingGUI(manager);
            guiInstance.setVisible(true);
        });

        runConsoleMode(manager);
    }

    private static void runConsoleMode(ParkingManager manager) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("==================================================");
        System.out.println("       PARKING MANAGEMENT SYSTEM (CONSOLE)        ");
        System.out.println("==================================================");

        boolean running = true;

        while (running) {
            System.out.println("\n--- CONSOLE MENU ---");
            System.out.println("1. Park Vehicle");
            System.out.println("2. Vehicle Exit");
            System.out.println("3. Display Slots");
            System.out.println("4. Exit Console Mode");
            System.out.print("Enter choice (1-4): ");

            if (!scanner.hasNextLine()) {
                break;
            }

            String choiceStr = scanner.nextLine().trim();

            switch (choiceStr) {
                case "1":
                    handleConsolePark(manager, scanner);
                    break;
                case "2":
                    handleConsoleExit(manager, scanner);
                    break;
                case "3":
                    handleConsoleDisplaySlots(manager);
                    break;
                case "4":
                    System.out.println("Exiting console mode. (GUI window remains active)");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please enter a number between 1 and 4.");
                    break;
            }

            if (guiInstance != null) {
                SwingUtilities.invokeLater(() -> guiInstance.refreshDashboard());
            }
        }
    }

    private static void handleConsolePark(ParkingManager manager, Scanner scanner) {
        System.out.println("\n--- PARK VEHICLE ---");
        System.out.print("Enter Vehicle Number (e.g. MH12AB1234): ");
        if (!scanner.hasNextLine()) return;
        String vehNo = scanner.nextLine().trim();

        System.out.print("Enter Owner Name: ");
        if (!scanner.hasNextLine()) return;
        String owner = scanner.nextLine().trim();

        System.out.println("Select Vehicle Type:");
        System.out.println("1. BIKE (2-Wheeler)");
        System.out.println("2. CAR (4-Wheeler)");
        System.out.print("Enter choice (1-2): ");
        if (!scanner.hasNextLine()) return;
        String typeChoice = scanner.nextLine().trim();

        VehicleType type;
        if ("1".equals(typeChoice)) {
            type = VehicleType.BIKE;
        } else if ("2".equals(typeChoice)) {
            type = VehicleType.CAR;
        } else {
            System.out.println("Error: Invalid vehicle type selected!");
            return;
        }

        try {
            Vehicle vehicle = new Vehicle(vehNo, owner, type);
            ParkingSlot assignedSlot = manager.parkVehicle(vehicle);

            System.out.println("\n>>> SUCCESS: Vehicle Parked Successfully!");
            System.out.println("Vehicle Number : " + vehicle.getVehicleNumber());
            System.out.println("Owner Name     : " + vehicle.getOwnerName());
            System.out.println("Vehicle Type   : " + vehicle.getVehicleType());
            System.out.println("Wing           : " + assignedSlot.getWing());
            System.out.println("Assigned Slot  : " + assignedSlot.getSlotNumber());

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("\n>>> PARKING ERROR: " + e.getMessage());
        }
    }

    private static void handleConsoleExit(ParkingManager manager, Scanner scanner) {
        System.out.println("\n--- VEHICLE EXIT ---");
        System.out.print("Enter Vehicle Number: ");
        if (!scanner.hasNextLine()) return;
        String vehNo = scanner.nextLine().trim();

        if (vehNo.isEmpty()) {
            System.out.println("Error: Vehicle number cannot be empty!");
            return;
        }

        ParkingSlot slot = manager.findSlotByVehicleNumber(vehNo);
        if (slot == null) {
            System.out.println("Error: Vehicle number '" + vehNo.toUpperCase() + "' was not found!");
            return;
        }

        System.out.println("Simulate duration? (Optional for testing)");
        System.out.println("0. Actual Time (Now)");
        System.out.println("1. 30 Minutes (1 Hr Billable)");
        System.out.println("2. 1 Hour 10 Minutes (2 Hrs Billable)");
        System.out.println("3. 2 Hours 20 Minutes (3 Hrs Billable)");
        System.out.print("Select duration (0-3): ");
        if (!scanner.hasNextLine()) return;
        String durChoice = scanner.nextLine().trim();

        LocalDateTime exitTime = LocalDateTime.now();
        LocalDateTime entryTime = slot.getVehicle().getEntryTime();

        switch (durChoice) {
            case "1":
                exitTime = entryTime.plusMinutes(30);
                break;
            case "2":
                exitTime = entryTime.plusHours(1).plusMinutes(10);
                break;
            case "3":
                exitTime = entryTime.plusHours(2).plusMinutes(20);
                break;
            default:
                exitTime = LocalDateTime.now();
                break;
        }

        try {
            ParkingTicket ticket = manager.exitVehicle(vehNo, exitTime);
            System.out.println("\n" + ticket.generateReceipt());
            System.out.println(">>> Slot " + ticket.getSlot().getSlotNumber() + " has been released.");
        } catch (IllegalArgumentException e) {
            System.out.println("\n>>> EXIT ERROR: " + e.getMessage());
        }
    }

    private static void handleConsoleDisplaySlots(ParkingManager manager) {
        System.out.println("\n--- PARKING SLOTS STATUS ---");
        System.out.printf("Total: %d | Available: %d | Occupied: %d\n\n",
                manager.getTotalSlots(), manager.getAvailableSlotsCount(), manager.getOccupiedSlotsCount());

        for (ParkingSlot slot : manager.getParkingSlots()) {
            System.out.println(slot);
        }
    }
}
