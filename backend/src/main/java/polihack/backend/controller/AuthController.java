package polihack.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import polihack.backend.dto.request.LoginRequest;
import polihack.backend.dto.response.LoginResponse; // Asigură-te că folosești clasa actualizată
import polihack.backend.dto.response.RegisterResponse;
import polihack.backend.model.Rescuer;
import polihack.backend.security.JwtUtil;
import polihack.backend.service.RescuerService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RescuerService rescuerService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(RescuerService rescuerService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.rescuerService = rescuerService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody Rescuer rescuer) {

        Rescuer savedRescuer = rescuerService.registerUser(rescuer);

        RegisterResponse response = new RegisterResponse(
                savedRescuer.getId(),
                savedRescuer.getFirstName(),
                savedRescuer.getLastName(),
                savedRescuer.getUsername(),
                savedRescuer.getPID(),
                savedRescuer.getTelephone(),
                savedRescuer.getEmail(),
                savedRescuer.getAssistanceTypeList()
        );

        System.out.println("Registration successful for: " + savedRescuer.getUsername());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        Rescuer rescuer = rescuerService.findByUsername(request.getUsername().toLowerCase());

        if(rescuer != null && passwordEncoder.matches(request.getPassword(), rescuer.getPassword())) {
            // TODO validation for jwtUtil
            String token = jwtUtil.generateToken(rescuer.getUsername(), rescuer.getPassword());
            System.out.println("Rescuer " + rescuer.getUsername() + " logged succefully!");

            // MODIFICARE: Returnează token-ul ȘI ID-ul
            return ResponseEntity.ok(new LoginResponse(token, rescuer.getId()));
        }
        return ResponseEntity.status(401).body("Invalid credentials!");
    }

}