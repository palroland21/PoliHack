package polihack.backend.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import polihack.backend.model.Rescuer;
import polihack.backend.repository.RescuerRepository;
import polihack.backend.service.RescuerService;

import java.util.List;

@Service
public class RescuerServiceImpl implements RescuerService {

    private final RescuerRepository rescuerRepository;
    private final PasswordEncoder passwordEncoder;

    public RescuerServiceImpl(RescuerRepository rescuerRepository, PasswordEncoder passwordEncoder) {
        this.rescuerRepository = rescuerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Rescuer> findAll() {
        return rescuerRepository.findAll();
    }

    @Override
    public Rescuer findByUsername(String username) {
        return rescuerRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Rescuer save(Rescuer rescuer) {
      return rescuerRepository.save(rescuer);
    }

    @Override
    public Rescuer registerUser(Rescuer rescuer) {
        rescuer.setUsername(rescuer.getUsername().toLowerCase());
        rescuer.setPassword(passwordEncoder.encode(rescuer.getPassword()));
        return rescuerRepository.save(rescuer);
    }
}
