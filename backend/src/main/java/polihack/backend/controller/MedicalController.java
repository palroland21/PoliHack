package polihack.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import polihack.backend.dto.request.MedicalVerificationRequest;
import polihack.backend.service.impl.MedicalService;

@RestController
@RequestMapping("/medical")
public class MedicalController {

    private final MedicalService medicalService;

    public MedicalController(MedicalService medicalService) {
        this.medicalService = medicalService;
    }

    @PostMapping("/verify/{rescuerId}")
    public ResponseEntity<String> verifyDoctor(@PathVariable Long rescuerId, @RequestBody MedicalVerificationRequest request) {
        boolean success = medicalService.processVerification(rescuerId, request);

        if (success) {
            return ResponseEntity.ok("Verification passed. Rescuer status updated.");
        } else {
            return ResponseEntity.badRequest().body("Verification failed.");
        }
    }
}