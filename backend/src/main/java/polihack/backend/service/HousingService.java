package polihack.backend.service;

import polihack.backend.dto.request.HousingRequest;
import polihack.backend.model.Housing;

import java.util.List;

public interface HousingService {
    Housing addHousing(HousingRequest request);
    List<Housing> getAllHousing();
}