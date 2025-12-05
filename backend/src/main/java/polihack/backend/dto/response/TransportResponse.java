package polihack.backend.dto.response;

import jakarta.persistence.*;
import polihack.backend.model.Rescuer;
import polihack.backend.model.enums.AvailabilityStatus;
import polihack.backend.model.enums.VehicleType;

public class TransportResponse {

    private Long id;
    private VehicleType vehicleType;
    private Integer capacity;
    private String destinations;
    private AvailabilityStatus availabilityStatus;
    private Long rescuerId;

    public TransportResponse(Long id, VehicleType vehicleType, Integer capacity, String destinations, AvailabilityStatus availabilityStatus, Long rescuerId) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.capacity = capacity;
        this.destinations = destinations;
        this.availabilityStatus = availabilityStatus;
        this.rescuerId = rescuerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getDestinations() {
        return destinations;
    }

    public void setDestinations(String destinations) {
        this.destinations = destinations;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public Long getRescuerId() {
        return rescuerId;
    }

    public void setRescuerId(Long rescuerId) {
        this.rescuerId = rescuerId;
    }
}
