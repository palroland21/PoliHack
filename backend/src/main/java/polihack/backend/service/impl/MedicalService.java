package polihack.backend.service.impl;

import org.springframework.stereotype.Service;
import polihack.backend.dto.request.MedicalVerificationRequest;
import polihack.backend.model.Rescuer; // <--- Importam Rescuer
import polihack.backend.repository.RescuerRepository; // <--- Importam Repository-ul corect

@Service
public class MedicalService {

    private final RescuerRepository rescuerRepository; // Folosim RescuerRepository

    public MedicalService(RescuerRepository rescuerRepository) {
        this.rescuerRepository = rescuerRepository;
    }

    public boolean processVerification(Long rescuerId, MedicalVerificationRequest request) {
        System.out.println("--- INITIATING MEDICAL VERIFICATION ---");
        System.out.println("Rescuer ID: " + rescuerId);
        System.out.println("Checking CMR Database for CUIM: " + request.getCuim());

        boolean isMockValid = request.getCuim() != null && !request.getCuim().isEmpty();

        if (isMockValid) {
            //cautam Rescuer-ul
            Rescuer rescuer = rescuerRepository.findById(rescuerId)
                    .orElseThrow(() -> new RuntimeException("Rescuer not found with id: " + rescuerId));

            rescuerRepository.save(rescuer);

            System.out.println("--- VERIFICATION SUCCESSFUL: Rescuer promoted ---");
            return true;
        }

        return false;
    }
}