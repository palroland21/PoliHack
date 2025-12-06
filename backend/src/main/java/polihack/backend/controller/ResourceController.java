package polihack.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import polihack.backend.dto.request.ResourceRequest;
import polihack.backend.dto.response.ResourceResponse;
import polihack.backend.model.Resource;
import polihack.backend.service.ResourceService;

import java.util.List;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping("/add")
    public ResponseEntity<ResourceResponse> addResource(@RequestBody ResourceRequest resourceRequest) {
        Resource newResource = resourceService.addResource(resourceRequest);

        ResourceResponse response = new ResourceResponse(
                newResource.getId(),
                newResource.getResourceTypes(),
                newResource.getQuantity(),
                newResource.getPickupLocation(),
                newResource.getAdditionalDetails(),
                newResource.getRescuer().getId()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Resource>> getAllResources() {
        return ResponseEntity.ok(resourceService.getAllResources());
    }
}