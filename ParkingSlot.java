public class ParkingSlot {
    private String slotNumber;
    private String wing;
    private VehicleType vehicleType;
    private boolean occupied;
    private Vehicle vehicle;

    public ParkingSlot(String slotNumber, String wing, VehicleType vehicleType) {
        this.slotNumber = slotNumber;
        this.wing = wing;
        this.vehicleType = vehicleType;
        this.occupied = false;
        this.vehicle = null;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public String getWing() {
        return wing;
    }

    public void setWing(String wing) {
        this.wing = wing;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void park(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.occupied = true;
    }

    public void release() {
        this.vehicle = null;
        this.occupied = false;
    }

    @Override
    public String toString() {
        if (occupied && vehicle != null) {
            return slotNumber + " [OCCUPIED: " + vehicle.getVehicleNumber() + "]";
        }
        return slotNumber + " [AVAILABLE]";
    }
}
