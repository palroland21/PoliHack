package polihack.backend.service;

import polihack.backend.dto.request.TransportRequest;
import polihack.backend.model.Transport;

import java.util.List;

public interface TransportService {
    Transport addTransport(TransportRequest request);
    List<Transport> getAllTransports();
}
