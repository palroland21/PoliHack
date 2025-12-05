package polihack.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import polihack.backend.model.Rescuer;

import java.util.Optional;

public interface RescuerRepository extends JpaRepository<Rescuer, Long> {
    Optional<Rescuer> findByUsername(String username);
}
