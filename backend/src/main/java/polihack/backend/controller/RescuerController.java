package polihack.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import polihack.backend.dto.response.RescuerOfferDTO;
import polihack.backend.service.impl.RescuerDashboardService; // Asigura-te ca importi service-ul nou

import java.util.List;

@RestController
@RequestMapping("/rescuer")
@CrossOrigin(origins = "http://localhost:5173") // Permite accesul din Vue
public class RescuerController {

    private final RescuerDashboardService dashboardService;

    public RescuerController(RescuerDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // 1. GET OFFERS (Fara parametru, userul e luat din Token in service)
    @GetMapping("/offers")
    public ResponseEntity<List<RescuerOfferDTO>> getMyOffers() {
        return ResponseEntity.ok(dashboardService.getMyOffers());
    }

    // 2. DELETE OFFER (Are nevoie de ID si TIP)
    @DeleteMapping("/offer/{type}/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable String type, @PathVariable Long id) {
        dashboardService.deleteOffer(id, type);
        return ResponseEntity.ok().build();
    }
}