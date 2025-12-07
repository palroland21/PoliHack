package polihack.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import polihack.backend.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUsername(String username);
}