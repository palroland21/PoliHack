package polihack.backend.service;

import polihack.backend.dto.request.ResourceRequest;
import polihack.backend.model.Resource;

import java.util.List;

public interface ResourceService {
    Resource addResource(ResourceRequest request);
    List<Resource> getAllResources();
}