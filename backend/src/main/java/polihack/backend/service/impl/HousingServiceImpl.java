package polihack.backend.service.impl;

import org.springframework.stereotype.Service;
import polihack.backend.dto.request.HousingRequest;
import polihack.backend.model.Housing;
import polihack.backend.model.Rescuer;
import polihack.backend.repository.HousingRepository;
import polihack.backend.repository.RescuerRepository;
import polihack.backend.service.HousingService;

import java.util.List;

@Service
public class HousingServiceImpl implements HousingService {

    private final HousingRepository housingRepository;
    private final RescuerRepository rescuerRepository;

    public HousingServiceImpl(HousingRepository housingRepository, RescuerRepository rescuerRepository) {
        this.housingRepository = housingRepository;
        this.rescuerRepository = rescuerRepository;
    }

    @Override
    public Housing addHousing(HousingRequest request) {
        Rescuer rescuer = rescuerRepository.findById(request.getRescuerId())
                .orElseThrow(() -> new RuntimeException("Rescuer not found with id: " + request.getRescuerId()));

        Housing housing = new Housing();
        housing.setHousingType(request.getHousingType());
        housing.setCapacity(request.getCapacity());
        housing.setAddress(request.getAddress());
        housing.setAvailablePeriod(request.getAvailablePeriod());
        housing.setFacilities(request.getFacilities());
        housing.setAdditionalNotes(request.getAdditionalNotes());
        housing.setRescuer(rescuer);

        return housingRepository.save(housing);
    }

    @Override
    public List<Housing> getAllHousing() {
        return housingRepository.findAll();
    }
}