package polihack.backend.dto.request;

import lombok.Data;
import polihack.backend.model.enums.AvailabilityStatus;
import polihack.backend.model.enums.HousingFacility;
import polihack.backend.model.enums.HousingType;

import java.util.Set;

@Data
public class HousingRequest {
    private HousingType housingType;
    private Integer capacity;
    private String address;
    private String availablePeriod;
    private Set<HousingFacility> facilities;
    private String additionalNotes;
    private Long rescuerId;
}