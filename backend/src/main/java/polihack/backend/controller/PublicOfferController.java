package polihack.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import polihack.backend.model.Housing;
import polihack.backend.model.Resource;
import polihack.backend.model.Transport;
import polihack.backend.model.enums.StatusType;
import polihack.backend.repository.HousingRepository;
import polihack.backend.repository.ResourceRepository;
import polihack.backend.repository.TransportRepository;

import java.util.List;

@RestController
@RequestMapping("/public") // Asta face ca toate linkurile sa inceapa cu /public
public class PublicOfferController {

    private final ResourceRepository resourceRepository;
    private final TransportRepository transportRepository;
    private final HousingRepository housingRepository;

    public PublicOfferController(ResourceRepository resourceRepository,
                                 TransportRepository transportRepository,
                                 HousingRepository housingRepository) {
        this.resourceRepository = resourceRepository;
        this.transportRepository = transportRepository;
        this.housingRepository = housingRepository;
    }

    // 1. Endpoint pentru RESURSE
    // URL: http://localhost:8080/public/resources
    @GetMapping("/resources")
    public ResponseEntity<List<Resource>> getAllResources() {
        // Returnam tot ce e in baza de date.
        // Frontend-ul va verifica campul 'status' sa vada daca e ocupat sau nu.
        return ResponseEntity.ok(resourceRepository.findAll());
    }

    // 2. Endpoint pentru TRANSPORT
    // URL: http://localhost:8080/public/transport
    @GetMapping("/transport")
    public ResponseEntity<List<Transport>> getAllTransport() {
        return ResponseEntity.ok(transportRepository.findAll());
    }

    // 3. Endpoint pentru CAZARE
    // URL: http://localhost:8080/public/housing
    @GetMapping("/housing")
    public ResponseEntity<List<Housing>> getAllHousing() {
        return ResponseEntity.ok(housingRepository.findAll());
    }


    @PostMapping("/reserve/{category}/{id}")
    public ResponseEntity<String> reserveItem(@PathVariable String category, @PathVariable Long id) {

        switch (category) {
            case "resources":
                return resourceRepository.findById(id).map(item -> {
                    if (item.getStatus() == StatusType.TAKEN) {
                        return ResponseEntity.badRequest().body("Already taken!");
                    }
                    item.setStatus(StatusType.TAKEN); // Sau "TAKEN" daca e String
                    resourceRepository.save(item);
                    return ResponseEntity.ok("Reserved successfully!");
                }).orElse(ResponseEntity.notFound().build());

            case "transport":
                return transportRepository.findById(id).map(item -> {
                    if (item.getStatus() == StatusType.TAKEN) {
                        return ResponseEntity.badRequest().body("Already taken!");
                    }
                    item.setStatus(StatusType.TAKEN);
                    transportRepository.save(item);
                    return ResponseEntity.ok("Reserved successfully!");
                }).orElse(ResponseEntity.notFound().build());

            case "housing":
                return housingRepository.findById(id).map(item -> {
                    if (item.getStatus() == StatusType.TAKEN) {
                        return ResponseEntity.badRequest().body("Already taken!");
                    }
                    item.setStatus(StatusType.TAKEN);
                    housingRepository.save(item);
                    return ResponseEntity.ok("Reserved successfully!");
                }).orElse(ResponseEntity.notFound().build());

            default:
                return ResponseEntity.badRequest().body("Invalid category");
        }
    }
}