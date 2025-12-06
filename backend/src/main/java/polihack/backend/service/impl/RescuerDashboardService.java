package polihack.backend.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import polihack.backend.dto.response.RescuerOfferDTO;
import polihack.backend.model.Rescuer;
import polihack.backend.repository.HousingRepository;
import polihack.backend.repository.RescuerRepository;
import polihack.backend.repository.ResourceRepository;
import polihack.backend.repository.TransportRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RescuerDashboardService {

    private final TransportRepository transportRepo;
    private final HousingRepository housingRepo;
    private final ResourceRepository resourceRepo;
    private final RescuerRepository rescuerRepo;

    public RescuerDashboardService(TransportRepository transportRepo, HousingRepository housingRepo,
                                   ResourceRepository resourceRepo, RescuerRepository rescuerRepo) {
        this.transportRepo = transportRepo;
        this.housingRepo = housingRepo;
        this.resourceRepo = resourceRepo;
        this.rescuerRepo = rescuerRepo;
    }

    public List<RescuerOfferDTO> getMyOffers() {
        // 1. Get username from security context
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. Find Rescuer ID from DB
        Rescuer currentRescuer = rescuerRepo.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Rescuer not found"));

        Long rescuerId = currentRescuer.getId();
        List<RescuerOfferDTO> combinedList = new ArrayList<>();

        //3. Map TRANSPORT
        combinedList.addAll(transportRepo.findAll().stream()
                .filter(t -> t.getRescuer().getId().equals(rescuerId))
                .map(t -> new RescuerOfferDTO(
                        t.getId(),
                        "transport",
                        "Transport: " + t.getVehicleType().toString(),
                        t.getCapacity() + " locuri | Dest: " + t.getDestinations(),
                        t.getAvailabilityStatus().toString()
                ))
                .collect(Collectors.toList()));

        //4. Map HOUSING
        combinedList.addAll(housingRepo.findAll().stream()
                .filter(h -> h.getRescuer().getId().equals(rescuerId))
                .map(h -> new RescuerOfferDTO(
                        h.getId(),
                        "housing",
                        "Cazare: " + h.getHousingType().toString(),
                        "Capacitate: " + h.getCapacity() + " pers | " + h.getAddress(),
                        "Perioada: " + h.getAvailablePeriod()
                ))
                .collect(Collectors.toList()));

        // 5. Map RESOURCES
        combinedList.addAll(resourceRepo.findAll().stream()
                .filter(r -> r.getRescuer().getId().equals(rescuerId))
                .map(r -> new RescuerOfferDTO(
                        r.getId(),
                        "resources",
                        "Resurse: " + r.getResourceTypes().toString(),
                        "Cantitate: " + r.getQuantity() + " | Locatie: " + r.getPickupLocation(),
                        "Activ"
                ))
                .collect(Collectors.toList()));

        return combinedList;
    }

    public void deleteOffer(Long id, String type) {
        switch (type) {
            case "transport" -> transportRepo.deleteById(id);
            case "housing" -> housingRepo.deleteById(id);
            case "resources" -> resourceRepo.deleteById(id);
            default -> throw new IllegalArgumentException("Tip necunoscut: " + type);
        }
    }
}