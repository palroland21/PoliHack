package polihack.backend.dto.request;

import polihack.backend.model.enums.ResourceType;

import java.util.Set;

public class ResourceRequest {
    private Set<ResourceType> resourceTypes;
    private String quantity;
    private String pickupLocation;
    private String additionalDetails;
    private Long rescuerId;

    public ResourceRequest(Set<ResourceType> resourceTypes, String quantity, String pickupLocation, String additionalDetails, Long rescuerId) {
        this.resourceTypes = resourceTypes;
        this.quantity = quantity;
        this.pickupLocation = pickupLocation;
        this.additionalDetails = additionalDetails;
        this.rescuerId = rescuerId;
    }

    public Set<ResourceType> getResourceTypes() {
        return resourceTypes;
    }

    public void setResourceTypes(Set<ResourceType> resourceTypes) {
        this.resourceTypes = resourceTypes;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(String additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public Long getRescuerId() {
        return rescuerId;
    }

    public void setRescuerId(Long rescuerId) {
        this.rescuerId = rescuerId;
    }
}