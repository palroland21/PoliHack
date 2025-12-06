package polihack.backend.dto.request;

import polihack.backend.model.enums.AvailabilityStatus;
import polihack.backend.model.enums.VehicleType;

public class TransportRequest {
    private VehicleType vehicleType;
    private Integer capacity;
    private String destinations;
    private AvailabilityStatus availabilityStatus;
    private Long rescuerId;

    public TransportRequest(VehicleType vehicleType, Integer capacity, String destinations, AvailabilityStatus availabilityStatus, Long rescuerId) {
        this.vehicleType = vehicleType;
        this.capacity = capacity;
        this.destinations = destinations;
        this.availabilityStatus = availabilityStatus;
        this.rescuerId = rescuerId;
    }

    // Getters and Setters
    public VehicleType getVehicleType() { return vehicleType; }
    public void setVehicleType(VehicleType vehicleType) { this.vehicleType = vehicleType; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public String getDestinations() { return destinations; }
    public void setDestinations(String destinations) { this.destinations = destinations; }

    public AvailabilityStatus getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) { this.availabilityStatus = availabilityStatus; }

    public Long getRescuerId() { return rescuerId; }
    public void setRescuerId(Long rescuerId) { this.rescuerId = rescuerId; }
}