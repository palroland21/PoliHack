package polihack.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import polihack.backend.model.enums.AvailabilityStatus;
import polihack.backend.model.enums.ResourceType;

import java.util.Set;

@Data
@AllArgsConstructor
public class ResourceResponse {
    private Long id;
    private Set<ResourceType> resourceTypes;
    private String quantity;
    private String pickupLocation;
    private String additionalDetails;
    private Long rescuerId;
}