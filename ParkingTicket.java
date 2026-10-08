import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParkingTicket {
    private Vehicle vehicle;
    private ParkingSlot slot;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private long durationHours;
    private double rate;
    private double totalFee;

    public ParkingTicket(Vehicle vehicle, ParkingSlot slot, LocalDateTime exitTime) {
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = vehicle.getEntryTime();
        this.exitTime = exitTime != null ? exitTime : LocalDateTime.now();
        calculateFee();
    }

    private void calculateFee() {
        Duration duration = Duration.between(entryTime, exitTime);
        long minutes = duration.toMinutes();
        long seconds = duration.getSeconds();

        if (seconds <= 0) {
            minutes = 1;
        }

        long hours = (minutes + 59) / 60;
        if (hours < 1) {
            hours = 1;
        }
        this.durationHours = hours;

        if (vehicle.getVehicleType() == VehicleType.BIKE) {
            this.rate = 10.0;
        } else {
            this.rate = 20.0;
        }
        this.totalFee = this.durationHours * this.rate;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getSlot() {
        return slot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public long getDurationHours() {
        return durationHours;
    }

    public double getRate() {
        return rate;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public String generateReceipt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------\n");
        sb.append("                 PARKING RECEIPT                  \n");
        sb.append("--------------------------------------------------\n");
        sb.append(String.format("%-20s : %s\n", "Vehicle Number", vehicle.getVehicleNumber()));
        sb.append(String.format("%-20s : %s\n", "Owner Name", vehicle.getOwnerName()));
        sb.append(String.format("%-20s : %s\n", "Vehicle Type", vehicle.getVehicleType()));
        sb.append(String.format("%-20s : %s\n", "Wing", slot.getWing()));
        sb.append(String.format("%-20s : %s\n", "Slot Number", slot.getSlotNumber()));
        sb.append(String.format("%-20s : %s\n", "Entry Time", entryTime.format(formatter)));
        sb.append(String.format("%-20s : %s\n", "Exit Time", exitTime.format(formatter)));
        sb.append(String.format("%-20s : %d Hour(s)\n", "Parking Duration", durationHours));
        sb.append(String.format("%-20s : ₹%.0f / hour\n", "Rate", rate));
        sb.append("--------------------------------------------------\n");
        sb.append(String.format("%-20s : ₹%.0f\n", "Total Fee", totalFee));
        sb.append("--------------------------------------------------\n");
        return sb.toString();
    }
}
