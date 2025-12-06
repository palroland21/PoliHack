package polihack.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import polihack.backend.model.enums.HousingFacility;
import polihack.backend.model.enums.HousingType;

import java.util.Set;

@Data
@AllArgsConstructor
public class HousingResponse {
    private Long id;
    private HousingType housingType;
    private Integer capacity;
    private String address;
    private String availablePeriod;
    private Set<HousingFacility> facilities;
    private String additionalNotes;
    private Long rescuerId;
}