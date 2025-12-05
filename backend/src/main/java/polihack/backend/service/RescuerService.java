package polihack.backend.service;

import polihack.backend.model.Rescuer;

import java.util.List;

public interface RescuerService {
    List<Rescuer> findAll();
    Rescuer save(Rescuer rescuer);
    Rescuer registerUser(Rescuer rescuer);
    Rescuer findByUsername(String username);
}
