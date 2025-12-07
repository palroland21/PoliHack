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

    @GetMapping("/transport/{rescuerId}")
    public ResponseEntity<List<RescuerOfferDTO>> getTransports(@PathVariable Long rescuerId) {
        return ResponseEntity.ok(dashboardService.getTransportOffers(rescuerId));
    }

    @GetMapping("/housing/{rescuerId}")
    public ResponseEntity<List<RescuerOfferDTO>> getHousing(@PathVariable Long rescuerId) {
        return ResponseEntity.ok(dashboardService.getHousingOffers(rescuerId));
    }

    @GetMapping("/resources/{rescuerId}")
    public ResponseEntity<List<RescuerOfferDTO>> getResources(@PathVariable Long rescuerId) {
        return ResponseEntity.ok(dashboardService.getResourceOffers(rescuerId));
    }

    @DeleteMapping("/offer/{type}/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable String type, @PathVariable Long id) {
        dashboardService.deleteOffer(id, type);
        return ResponseEntity.ok().build();
    }
}