import java.time.LocalDateTime;

public class Vehicle {
    private String vehicleNumber;
    private String ownerName;
    private VehicleType vehicleType;
    private LocalDateTime entryTime;

    public Vehicle(String vehicleNumber, String ownerName, VehicleType vehicleType) {
        this(vehicleNumber, ownerName, vehicleType, LocalDateTime.now());
    }

    public Vehicle(String vehicleNumber, String ownerName, VehicleType vehicleType, LocalDateTime entryTime) {
        this.vehicleNumber = vehicleNumber != null ? vehicleNumber.trim().toUpperCase() : "";
        this.ownerName = ownerName != null ? ownerName.trim() : "";
        this.vehicleType = vehicleType;
        this.entryTime = entryTime != null ? entryTime : LocalDateTime.now();
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber != null ? vehicleNumber.trim().toUpperCase() : "";
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName != null ? ownerName.trim() : "";
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    @Override
    public String toString() {
        return vehicleNumber + " (" + ownerName + ", " + vehicleType + ")";
    }
}
