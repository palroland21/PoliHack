package polihack.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import polihack.backend.dto.response.RescuerOfferDTO;
import polihack.backend.service.impl.RescuerDashboardService;
import java.util.List;

@RestController
@RequestMapping("/rescuer")
public class RescuerController {

    private final RescuerDashboardService dashboardService;

    public RescuerController(RescuerDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // 1. GET TRANSPORT -> Acum cere ID-ul rescuer-ului in URL
    // Exemplu apel: GET /rescuer/transport/1
    @GetMapping("/transport/{rescuerId}")
    public ResponseEntity<List<RescuerOfferDTO>> getTransports(@PathVariable Long rescuerId) {
        // Trimitem ID-ul mai departe catre Service
        return ResponseEntity.ok(dashboardService.getTransportOffers(rescuerId));
    }

    // 2. GET HOUSING -> La fel, cere ID-ul
    // Exemplu apel: GET /rescuer/housing/1
    @GetMapping("/housing/{rescuerId}")
    public ResponseEntity<List<RescuerOfferDTO>> getHousing(@PathVariable Long rescuerId) {
        return ResponseEntity.ok(dashboardService.getHousingOffers(rescuerId));
    }

    // 3. GET RESOURCES
    // Exemplu apel: GET /rescuer/resources/1
    @GetMapping("/resources/{rescuerId}")
    public ResponseEntity<List<RescuerOfferDTO>> getResources(@PathVariable Long rescuerId) {
        return ResponseEntity.ok(dashboardService.getResourceOffers(rescuerId));
    }

    // 4. DELETE OFFER
    // Aici ramane la fel, stergem oferta dupa ID-ul ei, nu conteaza rescuer-ul
    @DeleteMapping("/offer/{type}/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable String type, @PathVariable Long id) {
        dashboardService.deleteOffer(id, type);
        return ResponseEntity.ok().build();
    }
}