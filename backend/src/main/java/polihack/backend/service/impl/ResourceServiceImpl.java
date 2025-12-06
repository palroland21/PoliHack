package polihack.backend.service.impl;

import org.springframework.stereotype.Service;
import polihack.backend.dto.request.ResourceRequest;
import polihack.backend.model.Resource;
import polihack.backend.model.Rescuer;
import polihack.backend.repository.ResourceRepository;
import polihack.backend.repository.RescuerRepository;
import polihack.backend.service.ResourceService;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final RescuerRepository rescuerRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository, RescuerRepository rescuerRepository) {
        this.resourceRepository = resourceRepository;
        this.rescuerRepository = rescuerRepository;
    }

    @Override
    public Resource addResource(ResourceRequest request) {
        Rescuer rescuer = rescuerRepository.findById(request.getRescuerId())
                .orElseThrow(() -> new RuntimeException("Rescuer not found with id: " + request.getRescuerId()));

        Resource resource = new Resource();
        resource.setResourceTypes(request.getResourceTypes());
        resource.setQuantity(request.getQuantity());
        resource.setPickupLocation(request.getPickupLocation());
        resource.setAdditionalDetails(request.getAdditionalDetails());
        resource.setRescuer(rescuer);

        return resourceRepository.save(resource);
    }

    @Override
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }
}