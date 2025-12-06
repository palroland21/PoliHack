package polihack.backend.dto.request;

import lombok.Data;
import polihack.backend.model.enums.AvailabilityStatus;
import polihack.backend.model.enums.ResourceType;

import java.util.Set;

@Data
public class ResourceRequest {
    private Set<ResourceType> resourceTypes;
    private String quantity;
    private String pickupLocation;
    private String additionalDetails;
    private Long rescuerId;
}