package polihack.backend.service;

import org.springframework.stereotype.Service;
import polihack.backend.dto.request.TransportRequest;
import polihack.backend.model.Rescuer;
import polihack.backend.model.Transport;
import polihack.backend.repository.RescuerRepository;
import polihack.backend.repository.TransportRepository;

import java.util.List;

@Service
public class TransportServiceImpl implements TransportService {

    private final TransportRepository transportRepository;
    private final RescuerRepository rescuerRepository;

    public TransportServiceImpl(TransportRepository transportRepository, RescuerRepository rescuerRepository) {
        this.transportRepository = transportRepository;
        this.rescuerRepository = rescuerRepository;
    }

    @Override
    public Transport addTransport(TransportRequest request) {
        Rescuer rescuer = rescuerRepository.findById(request.getRescuerId())
                .orElseThrow(() -> new RuntimeException("Rescuer not found with id: " + request.getRescuerId()));

        Transport transport = new Transport();
        transport.setVehicleType(request.getVehicleType());
        transport.setCapacity(request.getCapacity());
        transport.setDestinations(request.getDestinations());
        transport.setAvailabilityStatus(request.getAvailabilityStatus());
        transport.setRescuer(rescuer);

        return transportRepository.save(transport);
    }

    @Override
    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }
}