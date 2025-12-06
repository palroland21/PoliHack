package polihack.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import polihack.backend.model.Company;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByUsername(String username);
}