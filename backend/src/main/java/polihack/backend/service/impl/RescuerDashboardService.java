package polihack.backend.service.impl;

import org.springframework.stereotype.Service;
import polihack.backend.dto.response.RescuerOfferDTO;
import polihack.backend.model.Rescuer;
import polihack.backend.repository.HousingRepository;
import polihack.backend.repository.RescuerRepository;
import polihack.backend.repository.ResourceRepository;
import polihack.backend.repository.TransportRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RescuerDashboardService {

    private final TransportRepository transportRepo;
    private final HousingRepository housingRepo;
    private final ResourceRepository resourceRepo;
    private final RescuerRepository rescuerRepo;

    public RescuerDashboardService(TransportRepository transportRepo, HousingRepository housingRepo, ResourceRepository resourceRepo, RescuerRepository rescuerRepo) {
        this.transportRepo = transportRepo;
        this.housingRepo = housingRepo;
        this.resourceRepo = resourceRepo;
        this.rescuerRepo = rescuerRepo;
    }

    public List<RescuerOfferDTO> getTransportOffers(Long rescuerId) {
        Rescuer rescuer = rescuerRepo.findById(rescuerId)
                .orElseThrow(() -> new RuntimeException("Rescuer not found with id: " + rescuerId));

        return transportRepo.findAll().stream()
                .filter(t -> t.getRescuer().getId().equals(rescuer.getId()))
                .map(t -> new RescuerOfferDTO(
                        t.getId(),
                        "transport",
                        "Transport: " + t.getVehicleType(),
                        t.getCapacity() + " locuri | " + t.getDestinations(),
                        t.getAvailabilityStatus().toString()
                ))
                .collect(Collectors.toList());
    }


    public List<RescuerOfferDTO> getHousingOffers(Long rescuerId) {
        Rescuer rescuer = rescuerRepo.findById(rescuerId)
                .orElseThrow(() -> new RuntimeException("Rescuer not found with id: " + rescuerId));

        return housingRepo.findAll().stream()
                .filter(h -> h.getRescuer().getId().equals(rescuer.getId()))
                .map(h -> new RescuerOfferDTO(
                        h.getId(),
                        "housing",
                        "Cazare: " + h.getHousingType(),
                        "Capacitate: " + h.getCapacity() + " | " + h.getAddress(),
                        h.getAvailablePeriod()
                ))
                .collect(Collectors.toList());
    }

    public List<RescuerOfferDTO> getResourceOffers(Long rescuerId) {
        Rescuer rescuer = rescuerRepo.findById(rescuerId)
                .orElseThrow(() -> new RuntimeException("Rescuer not found with id: " + rescuerId));

        return resourceRepo.findAll().stream()
                .filter(r -> r.getRescuer().getId().equals(rescuer.getId()))
                .map(r -> new RescuerOfferDTO(
                        r.getId(),
                        "resources",
                        "Resurse",
                        r.getQuantity() + " | " + r.getPickupLocation(),
                        "Activ"
                ))
                .collect(Collectors.toList());
    }

    public void deleteOffer(Long id, String type) {
        switch (type) {
            case "transport" -> transportRepo.deleteById(id);
            case "housing" -> housingRepo.deleteById(id);
            case "resources" -> resourceRepo.deleteById(id);
            default -> throw new IllegalArgumentException("Invalid type");
        }
    }
}