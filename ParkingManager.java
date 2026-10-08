import java.time.LocalDateTime;

public class ParkingManager {
    private ParkingSlot[] parkingSlots;

    public ParkingManager() {
        initializeSlots();
    }

    private void initializeSlots() {
        parkingSlots = new ParkingSlot[60];
        String[] wings = {"Wing A", "Wing B", "Wing C"};
        char[] wingCodes = {'A', 'B', 'C'};
        int index = 0;

        for (int w = 0; w < wings.length; w++) {
            String wingName = wings[w];
            char code = wingCodes[w];

            for (int i = 1; i <= 10; i++) {
                String slotNum = String.format("%c-2W-%02d", code, i);
                parkingSlots[index++] = new ParkingSlot(slotNum, wingName, VehicleType.BIKE);
            }

            for (int i = 1; i <= 10; i++) {
                String slotNum = String.format("%c-4W-%02d", code, i);
                parkingSlots[index++] = new ParkingSlot(slotNum, wingName, VehicleType.CAR);
            }
        }
    }

    public ParkingSlot[] getParkingSlots() {
        return parkingSlots;
    }

    public boolean isVehicleParked(String vehicleNumber) {
        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            return false;
        }
        String cleanNum = vehicleNumber.trim().toUpperCase();
        for (ParkingSlot slot : parkingSlots) {
            if (slot.isOccupied() && slot.getVehicle() != null) {
                if (slot.getVehicle().getVehicleNumber().equalsIgnoreCase(cleanNum)) {
                    return true;
                }
            }
        }
        return false;
    }

    public ParkingSlot findAvailableSlot(VehicleType type) {
        for (ParkingSlot slot : parkingSlots) {
            if (slot.getVehicleType() == type && !slot.isOccupied()) {
                return slot;
            }
        }
        return null;
    }

    public ParkingSlot parkVehicle(Vehicle vehicle) throws IllegalArgumentException, IllegalStateException {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle details cannot be null.");
        }
        if (vehicle.getVehicleNumber() == null || vehicle.getVehicleNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle number cannot be empty.");
        }
        if (vehicle.getOwnerName() == null || vehicle.getOwnerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Owner name cannot be empty.");
        }
        if (vehicle.getVehicleType() == null) {
            throw new IllegalArgumentException("Vehicle type must be specified.");
        }

        if (isVehicleParked(vehicle.getVehicleNumber())) {
            throw new IllegalArgumentException("Vehicle with number '" + vehicle.getVehicleNumber().toUpperCase() + "' is already parked inside!");
        }

        ParkingSlot slot = findAvailableSlot(vehicle.getVehicleType());
        if (slot == null) {
            throw new IllegalStateException("Parking Full! No available " + vehicle.getVehicleType() + " slots across Wing A, Wing B, or Wing C.");
        }

        slot.park(vehicle);
        return slot;
    }

    public ParkingSlot findSlotByVehicleNumber(String vehicleNumber) {
        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            return null;
        }
        String cleanNum = vehicleNumber.trim().toUpperCase();
        for (ParkingSlot slot : parkingSlots) {
            if (slot.isOccupied() && slot.getVehicle() != null) {
                if (slot.getVehicle().getVehicleNumber().equalsIgnoreCase(cleanNum)) {
                    return slot;
                }
            }
        }
        return null;
    }

    public ParkingTicket exitVehicle(String vehicleNumber) throws IllegalArgumentException {
        return exitVehicle(vehicleNumber, LocalDateTime.now());
    }

    public ParkingTicket exitVehicle(String vehicleNumber, LocalDateTime exitTime) throws IllegalArgumentException {
        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle number cannot be empty.");
        }
        ParkingSlot slot = findSlotByVehicleNumber(vehicleNumber);
        if (slot == null) {
            throw new IllegalArgumentException("Vehicle number '" + vehicleNumber.trim().toUpperCase() + "' was not found in the parking lot!");
        }

        Vehicle vehicle = slot.getVehicle();
        ParkingTicket ticket = new ParkingTicket(vehicle, slot, exitTime);
        slot.release();
        return ticket;
    }

    public int getTotalSlots() {
        return parkingSlots.length;
    }

    public int getOccupiedSlotsCount() {
        int count = 0;
        for (ParkingSlot slot : parkingSlots) {
            if (slot.isOccupied()) {
                count++;
            }
        }
        return count;
    }

    public int getAvailableSlotsCount() {
        return getTotalSlots() - getOccupiedSlotsCount();
    }

    public int getOccupiedCountByWing(String wingName) {
        int count = 0;
        for (ParkingSlot slot : parkingSlots) {
            if (slot.getWing().equalsIgnoreCase(wingName) && slot.isOccupied()) {
                count++;
            }
        }
        return count;
    }

    public int getAvailableCountByWing(String wingName) {
        int count = 0;
        for (ParkingSlot slot : parkingSlots) {
            if (slot.getWing().equalsIgnoreCase(wingName) && !slot.isOccupied()) {
                count++;
            }
        }
        return count;
    }
}
