package polihack.backend.service;

import polihack.backend.model.Client;

public interface ClientService {
    Client registerClient(Client client);
    Client findByUsername(String username);
}
