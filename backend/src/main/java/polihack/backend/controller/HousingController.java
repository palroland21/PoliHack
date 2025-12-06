package polihack.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import polihack.backend.dto.request.HousingRequest;
import polihack.backend.dto.response.HousingResponse;
import polihack.backend.model.Housing;
import polihack.backend.service.HousingService;

import java.util.List;

@RestController
@RequestMapping("/housing")
public class HousingController {

    private final HousingService housingService;

    public HousingController(HousingService housingService) {
        this.housingService = housingService;
    }

    @PostMapping("/add")
    public ResponseEntity<HousingResponse> addHousing(@RequestBody HousingRequest housingRequest) {
        Housing newHousing = housingService.addHousing(housingRequest);

        HousingResponse response = new HousingResponse(
                newHousing.getId(),
                newHousing.getHousingType(),
                newHousing.getCapacity(),
                newHousing.getAddress(),
                newHousing.getAvailablePeriod(),
                newHousing.getFacilities(),
                newHousing.getAdditionalNotes(),
                newHousing.getRescuer().getId()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Housing>> getAllHousing() {
        return ResponseEntity.ok(housingService.getAllHousing());
    }
}