package polihack.backend.dto.request;

import polihack.backend.model.enums.HousingFacility;
import polihack.backend.model.enums.HousingType;

import java.util.Set;


public class HousingRequest {
    private HousingType housingType;
    private Integer capacity;
    private String address;
    private String availablePeriod;
    private Set<HousingFacility> facilities;
    private String additionalNotes;
    private Long rescuerId;

    public HousingRequest(HousingType housingType, Integer capacity, String address, String availablePeriod, Set<HousingFacility> facilities, String additionalNotes, Long rescuerId) {
        this.housingType = housingType;
        this.capacity = capacity;
        this.address = address;
        this.availablePeriod = availablePeriod;
        this.facilities = facilities;
        this.additionalNotes = additionalNotes;
        this.rescuerId = rescuerId;
    }

    public HousingType getHousingType() {
        return housingType;
    }

    public void setHousingType(HousingType housingType) {
        this.housingType = housingType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvailablePeriod() {
        return availablePeriod;
    }

    public void setAvailablePeriod(String availablePeriod) {
        this.availablePeriod = availablePeriod;
    }

    public Set<HousingFacility> getFacilities() {
        return facilities;
    }

    public void setFacilities(Set<HousingFacility> facilities) {
        this.facilities = facilities;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public Long getRescuerId() {
        return rescuerId;
    }

    public void setRescuerId(Long rescuerId) {
        this.rescuerId = rescuerId;
    }
}