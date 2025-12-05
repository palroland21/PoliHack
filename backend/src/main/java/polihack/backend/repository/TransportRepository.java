package polihack.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import polihack.backend.model.Transport;

public interface TransportRepository extends JpaRepository<Transport, Long> {
}
