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
@RequestMapping("/public")
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

    @GetMapping("/resources")
    public ResponseEntity<List<Resource>> getAllResources() {
        //return tot ce e in baza de date la resurse
        return ResponseEntity.ok(resourceRepository.findAll());
    }


    @GetMapping("/transport")
    public ResponseEntity<List<Transport>> getAllTransport() {
        return ResponseEntity.ok(transportRepository.findAll());
    }

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
                    item.setStatus(StatusType.TAKEN);
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