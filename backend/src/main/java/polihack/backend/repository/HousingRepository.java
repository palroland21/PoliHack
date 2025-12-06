package polihack.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import polihack.backend.model.Housing;

public interface HousingRepository extends JpaRepository<Housing, Long> {
}