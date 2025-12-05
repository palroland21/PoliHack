package polihack.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import polihack.backend.dto.request.TransportRequest;
import polihack.backend.dto.response.TransportResponse;
import polihack.backend.model.Transport;
import polihack.backend.service.TransportService;

import java.util.List;

@RestController
@RequestMapping("/transport")
public class TransportController {

    private final TransportService transportService;

    public TransportController(TransportService transportService) {
        this.transportService = transportService;
    }

    @PostMapping("/add")
    public ResponseEntity<TransportResponse> addTransport(@RequestBody TransportRequest transportRequest) {
        Transport newTransport = transportService.addTransport(transportRequest);

        TransportResponse response = new TransportResponse(
                newTransport.getId(),
                newTransport.getVehicleType(),
                newTransport.getCapacity(),
                newTransport.getDestinations(),
                newTransport.getAvailabilityStatus(),
                newTransport.getRescuer().getId()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transport>> getAllTransports() {
        return ResponseEntity.ok(transportService.getAllTransports());
    }
}