package polihack.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import polihack.backend.dto.request.LoginRequest;
import polihack.backend.dto.response.LoginResponse;
import polihack.backend.dto.response.ClientRegisterResponse;
import polihack.backend.model.Client;
import polihack.backend.security.JwtUtil;
import polihack.backend.service.ClientService;

@RestController
@RequestMapping("/auth/client") // Rute separate pentru Client
public class ClientAuthController {

    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ClientAuthController(ClientService clientService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<ClientRegisterResponse> registerClient(@RequestBody Client client) {

        Client savedClient = clientService.registerClient(client);

        ClientRegisterResponse response = new ClientRegisterResponse(
                savedClient.getId(),
                savedClient.getFirstName(),
                savedClient.getLastName(),
                savedClient.getUsername(),
                savedClient.getCnp(),
                savedClient.getTelephone(),
                savedClient.getEmail()
        );

        System.out.println("Client registration successful for: " + savedClient.getUsername());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginClient(@RequestBody LoginRequest request) {
        Client client = clientService.findByUsername(request.getUsername().toLowerCase());

        if(client != null && passwordEncoder.matches(request.getPassword(), client.getPassword())) {

            String token = jwtUtil.generateToken(client.getUsername(), client.getPassword());
            System.out.println("Client " + client.getUsername() + " logged successfully!");

            // Returnează token-ul și ID-ul clientului
            return ResponseEntity.ok(new LoginResponse(token, client.getId()));
        }
        return ResponseEntity.status(401).body("Invalid credentials!");
    }
}