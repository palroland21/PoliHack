package polihack.backend.controller;

import polihack.backend.dto.request.TriageRequest;
import polihack.backend.dto.response.TriageResponse;
import polihack.backend.service.impl.AiTriageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/triage")
@CrossOrigin(origins = "*")
public class AiTriageController {

    private final AiTriageService triageService;

    public AiTriageController(AiTriageService triageService) {
        this.triageService = triageService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<TriageResponse> analyze(@RequestBody TriageRequest request) {
        TriageResponse response = triageService.analyzeSymptoms(request);
        return ResponseEntity.ok(response);
    }
}