package polihack.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import polihack.backend.model.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}